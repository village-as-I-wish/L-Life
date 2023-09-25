package kosa.com.suntofu.L_LIFE.community.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import kosa.com.suntofu.L_LIFE.community.dao.CommunityDao;
import kosa.com.suntofu.L_LIFE.community.vo.BookPageRequestVo;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

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
    public List<BookVo> selectBooks() {
        List<BookVo> books = communityDao.selectBooks();
        log.info("books returned {}", books);
        return books;
    }

    @Override
    public BookVo selectBookDetailById(int bookId) {

        return communityDao.selectBookDetailById(bookId);
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
    public int createBook(BookRequestVo bookRequestVo){

        try{
            int bpResult = -1;
            int bfResult = -1;

            communityDao.insertBook(bookRequestVo);
            int insertedBookId = bookRequestVo.getBookId();
            log.info("[책 등록 ] 데이터 삽입 성공 {}", insertedBookId);
            return insertedBookId;

        }catch(Exception e) {
            log.info("[책등록 ] 데이터 삽입 오류 {} ", e.getMessage());
            return -1;
        }
    }

    @Override
    @Transactional
    public int createPage(BookPageRequestVo bookPageRequestVo) {

        if(bookPageRequestVo.getFile() != null){
            String imgUrl = uploadFile(bookPageRequestVo.getFile(), "L-life-BOOK");
            bookPageRequestVo.setBpImg(imgUrl);
        }
        if(bookPageRequestVo.getAiImageFile() != null){
            File file = new File(bookPageRequestVo.getBpTitle());
            try {
                FileUtils.copyURLToFile(new URL(bookPageRequestVo.getAiImageFile()), file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String AIimgUrl = uploadNormalFile(file);
            System.out.println("생성된 AI url : "+AIimgUrl);

//            String AIimgUrl = uploadFile(bookPageRequestVo.getAiImageFile(), "L-life-BOOK-AI");
            bookPageRequestVo.setBpAiImg(AIimgUrl);
        }

        try{
            int bpResult = -1;
            int bfResult = 0;

            bpResult = communityDao.insertBookPage(bookPageRequestVo);

            if(bookPageRequestVo.getLfId() != null){
                bfResult = communityDao.insertBFurniture(bookPageRequestVo);
            }

            if(bpResult == -1 || bfResult == -1){
                throw new Exception("[북페이지 등록] 데이터 삽입 오류");
            }
            log.info("[책 페이지 등록 ] 성공 페이지 수  : {}, 성공 가구 수 : {}  ", bpResult, bfResult );
            return 1;
        }catch(Exception e) {
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

    private String uploadFile(MultipartFile file,String filePath){
        String fileName = filePath + "/" + createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "사진 파일 업로드에 실패했습니다.");
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public String uploadNormalFile(File file) {
        String fileName = "ai/" + file.getName();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.length()); // 파일 크기 설정
        // 파일의 MIME 타입 설정 (예: image/jpeg)
        // MIME 타입을 정확하게 알고 있다면 수동으로 설정할 수 있습니다.
         objectMetadata.setContentType("image/jpeg");
        try (InputStream inputStream = new FileInputStream(file)) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, objectMetadata);
            PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        // 업로드된 파일의 URL 생성
        String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
        return fileUrl;
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
