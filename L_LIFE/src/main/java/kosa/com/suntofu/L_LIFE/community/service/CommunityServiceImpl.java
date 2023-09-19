package kosa.com.suntofu.L_LIFE.community.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kosa.com.suntofu.L_LIFE.community.dao.CommunityDao;
import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements  CommunityService{


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final CommunityDao communityDao;
    @Override
    public String uploadFileImg(MultipartFile file) {
        try {
            String fileName=file.getOriginalFilename();
            String fileUrl= "https://" + bucket + "/test" +fileName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductVo> getProductByStyle(int lfMoodId) {
        return communityDao.selectProductByStyle(lfMoodId);
    }

    @Override
    public BookVo selectBookDetailById(int bookId) {
        return communityDao.selectBookDetailById(bookId);
    }

    @Override
    public List<BookVo> selectBooks() {
        return communityDao.selectBooks();
    }
}
