package kosa.com.suntofu.L_LIFE.standard.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewImgVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewRequestVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewVo;
import kosa.com.suntofu.L_LIFE.constant.CacheKey;
import kosa.com.suntofu.L_LIFE.premium.vo.PackageVo;
import kosa.com.suntofu.L_LIFE.standard.dao.StandardDAO;
import kosa.com.suntofu.L_LIFE.common.util.CartReturn;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    private final StandardDAO standardDAO;

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public StandardPaginationVo calculateAndSetOffset(StandardPaginationVo standardPaginationVo) {
        if(standardPaginationVo.getPage() == 0) {
            standardPaginationVo.setPage(1);
        }
        int offset = 20 * (standardPaginationVo.getPage() - 1);
        standardPaginationVo.setOffset(offset);
        return standardPaginationVo;
    }

    @Override
    public int calculatePaginationNum(int totalNum) {
        return (int)Math.ceil((double)totalNum / 20);
    }

    @Override
    public List<StandardVo> getAllStandard(StandardPaginationVo standardPaginationVo) {
        standardPaginationVo = calculateAndSetOffset(standardPaginationVo);
        return standardDAO.selectAllStandard(standardPaginationVo);
    }

    @Override
    public int getAllStandardPagination(StandardPaginationVo standardPaginationVo) {
        return standardDAO.selectAllStandardPagination(standardPaginationVo);
    }

    @Override
    public List<StandardLiveVo> getAllLiveStream() {
        return standardDAO.selectAllLiveStream();
    }

    @Override
    public List<StandardVo> getStandardProductByCategory(StandardPaginationVo standardPaginationVo) {
        standardPaginationVo = calculateAndSetOffset(standardPaginationVo);
        return standardDAO.selectStandardProductByCategory(standardPaginationVo);
    }

    @Override
    public int getStandardProductByCategoryByPagination(StandardPaginationVo standardPaginationVo) {
        return standardDAO.selectStandardProductByCategoryByPagination(standardPaginationVo);
    }

    @Override
    public List<StandardVo> getStandardProductByKeyword(StandardPaginationVo standardPaginationVo) {
        standardPaginationVo = calculateAndSetOffset(standardPaginationVo);
        return standardDAO.selectStandardProductByKeyword(standardPaginationVo);
    }
    @Override
    public int getStandardProductByKeywordByPagination(StandardPaginationVo standardPaginationVo) {
        return standardDAO.selectStandardProductByKeywordByPagination(standardPaginationVo);
    }

    @Override
    public List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo) {
        return standardDAO.searchStandardProductByFilter(requestVo);
    }

    @Override
    public StandardDetailVo getStandardDetailById(int lfId) {

        String key= CacheKey.PRODUCT_DETAIL_BY_ID + lfId;
        StandardDetailVo result = getCachedSearchResult(key);
        if (result != null){
            log.info("[REDIS] Product Detail - Cache Hit - {}", key);
            return result;
        }else{
            log.info("[REDIS] Product Detail - Cache Miss - {}", key);
            result = standardDAO.selectStandardDetailById(lfId);
            cacheProduct(key, result);
            return result;
        }
    }
    private StandardDetailVo getCachedSearchResult(String cacheKey) {
        @SuppressWarnings("unchecked")
        StandardDetailVo cachedData = (StandardDetailVo) redisTemplate.opsForValue().get(cacheKey);
        return cachedData;
    }
    private void cacheProduct(String cacheKey, StandardDetailVo cachingData) {
        redisTemplate.opsForValue().set(cacheKey,cachingData, 1, TimeUnit.DAYS);  // 하루동안 캐싱
        log.info("[REDIS] Product Detail - Cache 저장 - {}", cacheKey);
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
    public List<StandardVo> getStandardRecommendation(StandardDetailVo standardDetailVo) {
        return standardDAO.selectStandardRecommendation(standardDetailVo);
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
    public int putProductToCart(CartItemVO cartItemVO) {
        try{
            standardDAO.insertProductToCart(cartItemVO);
            return CartReturn.CART_ADD_SUCCESS;
        }catch(Exception e){
            if (e.getCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getCause();
                int oracleErrorCode = sqlException.getErrorCode();
                if (oracleErrorCode == 20002) {
                    log.info("[ 스탠다드 장바구니 ] 스탠다드 구독권이 없는 상태");
                    return CartReturn.NO_SUBSCRIPTION_EXIST;
                } else {
                    log.info("[스탠다드 구독 가입 - 플랜 ] 데이터 삽입 오류 발생 ");
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                    return CartReturn.CART_ADD_ERROR;
                }
            }
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            log.info("[스탠다드 장바구니] 데이터 삽입 오류 발생 ");
            return CartReturn.CART_ADD_ERROR;
        }

    }

    @Transactional
    @Override
    public int putProductsToCart(CartsRequestVo cartRequestVo) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", cartRequestVo.getCarts());
        return standardDAO.insertProductsToCart(map);
    }
    @Transactional
    @Override
    public ReviewVo createReview(ReviewRequestVo reviewRequestVo) throws Exception {
        List<String> fileNameList = new ArrayList<>();

        reviewRequestVo.getFiles().forEach(file -> {
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
            standardDAO.insertReview(reviewRequestVo);
            int insertedReviewId = reviewRequestVo.getLfReviewId();

            log.info("[리뷰 등록 ] 데이터 삽입 성공 {}", insertedReviewId);

            List<ReviewImgVo> reviewImgList = new ArrayList<>();

            if(fileNameList.size() !=0){
                for( String reviewImgUrl : fileNameList){
                    reviewImgList.add(ReviewImgVo.builder().rImgUrl(reviewImgUrl).lfReviewId(insertedReviewId).build());
                }
                standardDAO.insertReviewImg(reviewImgList);
            }
            ReviewVo reviewVo = standardDAO.selectReviewById(insertedReviewId);
            return reviewVo;
        }catch(Exception e) {
            log.info("[리뷰 등록 ] 데이터 삽입 오류 {} ", e.getStackTrace().toString());
            throw new Exception("데이터 삽입 오류");
        }
    }

    @Override
    public List<ReviewVo> getReviews(int lfId) {
        return standardDAO.selectAllReviews(lfId);
    }

    @Override
    public int deleteReview(int lfReviewId) {
        try{
            return standardDAO.deleteReview(lfReviewId);
        }catch(Exception e){
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