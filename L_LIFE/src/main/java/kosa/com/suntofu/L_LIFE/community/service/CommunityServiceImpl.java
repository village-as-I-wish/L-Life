package kosa.com.suntofu.L_LIFE.community.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kosa.com.suntofu.L_LIFE.community.dao.CommunityDao;
import kosa.com.suntofu.L_LIFE.community.vo.BookRequestVo;
import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import kosa.com.suntofu.L_LIFE.constant.CacheKey;
import kosa.com.suntofu.L_LIFE.premium.vo.PackageVo;
import kosa.com.suntofu.L_LIFE.standard.vo.ReviewImgVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        return communityDao.selectBooks();
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


    @Override
    public int createBook(BookRequestVo bookRequestVo) {
        // 페이지별 이미지 업로드
        if (bookRequestVo.getPages() != null){
            bookRequestVo.getPages().forEach(page -> {
                if(page.getFile() != null) {
                    String fileName = "book/" + createFileName(page.getFile().getOriginalFilename());

                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(page.getFile().getSize());
                    objectMetadata.setContentType(page.getFile().getContentType());

                    try (InputStream inputStream = page.getFile().getInputStream()) {
                        amazonS3Client.putObject(bucket, fileName, page.getFile().getInputStream(), objectMetadata);
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
                    }
                    page.setBpImg(amazonS3Client.getUrl(bucket, fileName).toString());
                }
            });

        }
        try{
            int bpResult = -1;
            int bfResult = -1;

            communityDao.insertBook(bookRequestVo);
            int insertedBookId = bookRequestVo.getBookId();
            log.info("[책 등록 ] 데이터 삽입 성공 {}", insertedBookId);

            //삽입된 bookId값으로 세팅
            if(bookRequestVo.getPages() !=null) {
                bookRequestVo.getPages().forEach(page -> {
                    page.setBookId(insertedBookId);
                });
                bpResult = communityDao.insertBookPages(bookRequestVo.getPages());
            }
            if(bookRequestVo.getFurnitures() !=null){
                bookRequestVo.getFurnitures().forEach(furniture->{
                    furniture.setBookId(insertedBookId);
                });
                bfResult = communityDao.insertBFurniture(bookRequestVo.getFurnitures());

            }
            log.info("[책 페이지 등록 ] 성공 페이지 수  : {}, 성공 가구 수 : {}  ", bpResult, bfResult );
            return 1;

        }catch(Exception e) {
            log.info("[북페이지 & 북 상품 ] 데이터 삽입 오류 {} ", e.getStackTrace());
            log.info("[북페이지 & 북 상품 ] 데이터 삽입 오류 {} ", e.getMessage());
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
