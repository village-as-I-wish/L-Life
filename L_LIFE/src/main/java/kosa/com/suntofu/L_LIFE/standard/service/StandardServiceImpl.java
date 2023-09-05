package kosa.com.suntofu.L_LIFE.standard.service;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import kosa.com.suntofu.L_LIFE.standard.dao.StandardDAO;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    private final StandardDAO standardDAO;

    @Override
    public List<StandardVo> getAllStandard() {

        return standardDAO.selectAllStandard();
    }

    @Override
    public List<StandardLiveVo> getAllLiveStream() {

        return standardDAO.selectAllLiveStream();
    }

    @Override
    public List<StandardVo> getStandardByCategory(int fCategoryId) {

        return standardDAO.selectStandardProductByCategory(fCategoryId);
    }

    @Override
    public List<StandardVo> getStandardProductByKeyword(String keyword) {

        return standardDAO.selectStandardProductByKeyword(keyword);
    }

    @Override
    public List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo) {

        return standardDAO.searchStandardProductByFilter(requestVo);
    }

    @Override
    public StandardDetailVo getStandardDetailById(int lfId) {

        return standardDAO.selectStandardDetailById(lfId);
    }

    @Override
    public List<StandardOptionVo> getStandardOptionById(int lfId) {

        return standardDAO.selectStandardOptionById(lfId);
    }

    @Override
    public List<StandardRefurVo> getStandardRefurById(int lfId) {

        return standardDAO.selectStandardRefurById(lfId);
    }


    @Override
    public List<StandardVo> getStandardRecommendation(int lfId) {

        return standardDAO.selectStandardRecommendation(lfId);
    }

    @Override
    public int getStandardStockAmount(int lfOptId, int lfId) {

        StandardRestockVo restock = standardDAO.selectStandardStockAmount(lfOptId, lfId);
        if (restock != null) {
            int stockAmount = restock.getStockAmount();
            return stockAmount;
        }
        return -1;
    }

    @Override
    public int putOptionToReservation(StandardOptionVo standardOptionVo) {

        return standardDAO.insertOptionToReservation(standardOptionVo);
    }

    @Override
    public int putProductToCart(StandardSubscriptionVo standardSubscriptionVo) {

        return standardDAO.insertProductToCart(standardSubscriptionVo);
    }

    @Transactional
    @Override
    public int createReview(ReviewVo reviewVo) {
        List<String> fileNameList = new ArrayList<>();

        reviewVo.getFiles().forEach(file -> {
            String fileName = "review/" +createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(bucket,fileName,file.getInputStream(),objectMetadata);
            } catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            fileNameList.add(amazonS3Client.getUrl(bucket, fileName).toString());
        });

        try{
            standardDAO.insertReview(reviewVo);
            int insertedReviewId = reviewVo.getLfReviewId();

            log.info("[리뷰 등록 ] 데이터 삽입 성공 {}", insertedReviewId);

            List<ReviewImgVo> reviewImgList = new ArrayList<>();

            if(fileNameList.size() !=0){
                for( String reviewImgUrl : fileNameList){
                    reviewImgList.add(ReviewImgVo.builder().rImgUrl(reviewImgUrl).lfReviewId(insertedReviewId).build());
                }
                return standardDAO.insertReviewImg(reviewImgList);
            }
            return 1;
        }catch(Exception e) {
            log.info("[리뷰 등록 ] 데이터 삽입 오류 {} ", e.getStackTrace().toString());
            return -1;
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

}