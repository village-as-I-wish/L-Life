package kosa.com.suntofu.L_LIFE.community.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kosa.com.suntofu.L_LIFE.community.dao.CommunityDao;
import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import kosa.com.suntofu.L_LIFE.constant.CacheKey;
import kosa.com.suntofu.L_LIFE.premium.vo.PackageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements  CommunityService{


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;
    private final CommunityDao communityDao;

    private final RedisTemplate<String, Object> redisTemplate;

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
        List<BookVo> books = communityDao.selectBooks();
        log.info("books returned {}", books);
        return books;
    }

    @Override
    public List<ProductVo> getProductByCategoryId(int categoryId) {

        List<ProductVo> cachedProduct = getCachedSearchResult(CacheKey.PRODUCTS_BY_CATEGORY+categoryId);
        if(cachedProduct != null){
            log.info("[REDIS] Get Proudct By CategoryId - Cache Hit - {}", CacheKey.PRODUCTS_BY_CATEGORY + categoryId);
            return cachedProduct;
        }
        log.info("[REDIS] Get Proudct By CategoryId - Cache Miss - {}", CacheKey.PRODUCTS_BY_CATEGORY + categoryId);
        List<ProductVo> products = communityDao.getProductByCategoryId(categoryId);
        cacheProducts(CacheKey.PRODUCTS_BY_CATEGORY+categoryId, products);
        return products;
    }

    @Override
    public List<ProductVo> getProductByKeyword(String keyword) {
        log.info("KeywordSearch {} ", keyword);
        List<ProductVo> products = communityDao.selectProductByKeyword(keyword);
        return products;
    }

    private void cacheProducts(String cacheKey, List<ProductVo> cachingData) {
        redisTemplate.opsForValue().set(cacheKey,cachingData, 1, TimeUnit.DAYS);  // 하루동안 캐싱
        log.info("[REDIS] Product By CategoryId - Cache 저장 - {}", cacheKey);
    }

    private List<ProductVo> getCachedSearchResult(String cacheKey) {
        @SuppressWarnings("unchecked")
        List<ProductVo> cachedData = (List<ProductVo>) redisTemplate.opsForValue().get(cacheKey);
        return cachedData;
    }
}
