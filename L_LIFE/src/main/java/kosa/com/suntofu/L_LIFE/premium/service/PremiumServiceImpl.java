package kosa.com.suntofu.L_LIFE.premium.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewImgVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewRequestVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewVo;
import kosa.com.suntofu.L_LIFE.constant.CacheKey;
import kosa.com.suntofu.L_LIFE.premium.dao.PremiumDao;
import kosa.com.suntofu.L_LIFE.premium.vo.*;
import kosa.com.suntofu.L_LIFE.common.util.CartReturn;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PremiumServiceImpl implements PremiumService{


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;


    private final PremiumDao premiumDao;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<PremiumVo> selectPremiumProductList(PaginationVo paginationVo) {
        paginationVo = calculateAndSetOffset(paginationVo);
        return premiumDao.selectPremiumProductList(paginationVo);
    }

    @Override
    public int selectProductCountPagination(PaginationVo paginationVo) {
        return premiumDao.selectProductCountPagination(paginationVo);
    }

    @Override
    public PaginationVo calculateAndSetOffset(PaginationVo paginationVo) {
        if(paginationVo.getPage() == 0) {
            paginationVo.setPage(1);
        }
        int offset = 20 * (paginationVo.getPage() - 1);
        paginationVo.setOffset(offset);
        return paginationVo;
    }

    @Override
    public int calculatePaginationNum(int totalNum) {
        return (int)Math.ceil((double)totalNum / 20);
    }

    @Override
    public List<PremiumVo> selectProductByCategory(PaginationVo paginationVo) {
        paginationVo = calculateAndSetOffset(paginationVo);
        return premiumDao.selectProductByCategory(paginationVo);
    }

    @Override
    public int selectProductCountByCategoryByPagination(PaginationVo paginationVo) {
        return premiumDao.selectProductCountByCategoryByPagination(paginationVo);
    }

    @Override
    public List<PremiumVo> selectProductByKeyword(PaginationVo paginationVo) {
        paginationVo = calculateAndSetOffset(paginationVo);
        return premiumDao.selectProductByKeyword(paginationVo);
    }

    @Override
    public int selectProductByKeywordPagination(PaginationVo paginationVo) {
        return premiumDao.selectProductByKeywordPagination(paginationVo);
    }

    @Override
    public List<PremiumVo> selectProductByFilter(PaginationVo paginationVo) {
        paginationVo = calculateAndSetOffset(paginationVo);
        return premiumDao.selectProductByFilter(paginationVo);
    }

    @Override
    public PremiumVo selectPremiumProductDetailById(int lfId) {
        return premiumDao.selectPremiumProductDetailById(lfId);
    }

    @Override
    public List<PremiumOptionVo> selectPremiumOptionById(int lfId) {
        return premiumDao.selectPremiumOptionById(lfId);
    }

    @Override
    public int selectPremiumStockAmount(int lfOptId, int lfId) {
        PremiumOptionVo restock = premiumDao.selectPremiumStockAmount(lfOptId, lfId);
        if (restock != null) {
            int stockAmount = restock.getStockAmount();
            return stockAmount;
        }
        return -1;
    }

    @Override
    public int insertOptionToReservation(PremiumOptionVo premiumOptionVo) {
        return premiumDao.insertOptionToReservation(premiumOptionVo);
    }

    @Override
    public List<PremiumVo> selectPremiumRecommendation(PremiumVo premiumVo) {
        return premiumDao.selectPremiumRecommendation(premiumVo);
    }

    @Override
    public int insertPremiumProductToCart(CartItemVO cartItemVO) {
        try{
            premiumDao.insertPremiumProductToCart(cartItemVO);
            return CartReturn.CART_ADD_SUCCESS;
        }catch(Exception e){
            if (e.getCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getCause();
                int oracleErrorCode = sqlException.getErrorCode();
                if (oracleErrorCode == 20002) {
                    log.info("[ 프리미엄 장바구니 ] 프리미엄 구독권이 없는 상태");
                    return CartReturn.NO_SUBSCRIPTION_EXIST;
                } else {
                    log.info("[프리미엄 장바구니 ] 데이터 삽입 오류 발생 ");
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                    return CartReturn.CART_ADD_ERROR;
                }
            }
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            log.info("[ 프리미엄 장바구니 ] 데이터 삽입 오류 발생 ");
            return CartReturn.CART_ADD_ERROR;
        }
    }

    @Override
    public List<PackageVo> getMDPickPackages() {
        List<PackageVo> packages = getCachedSearchResult(CacheKey.MDPICK_LLIFE_PACKAGES);
        if (packages != null){
            log.info("[REDIS] MD PICK PACKAGES  - Cache Hit - {}", CacheKey.MDPICK_LLIFE_PACKAGES);
            return packages;
        }else{
            log.info("[REDIS]  MD PICK PACKAGES - Cache Miss - {}", CacheKey.MDPICK_LLIFE_PACKAGES);
            packages = premiumDao.selectMDPickPackages();
            cachePackages(CacheKey.MDPICK_LLIFE_PACKAGES, packages);
            return packages;
        }
    }

    /**
     * Package List - Promotion Package load 메서드
     * @return List<PackageVo>
     */
    @Override
    public List<PackageVo> getPromotionPackages() {

        List<PackageVo> packages = getCachedSearchResult(CacheKey.PROMOTION_LLIFE_PACKAGES);

        if (packages != null){
            log.info("[REDIS] PROMOTION PACKAGES - Cache Hit - {}", CacheKey.PROMOTION_LLIFE_PACKAGES);
            return packages;
        }else{
            log.info("[REDIS] PROMOTION PACKAGES - Cache Miss - {}", CacheKey.PROMOTION_LLIFE_PACKAGES);
            packages = premiumDao.selectPromotionPackages();
            cachePackages(CacheKey.PROMOTION_LLIFE_PACKAGES, packages);
            return packages;
        }
    }

    @Override
    public List<PackageVo> getRecommendationPackages() {

        List<PackageVo> packages = getCachedSearchResult(CacheKey.RECOMMENDATION_LLIFE_PACKAGES);

        if (packages != null){
            log.info("[REDIS] RECOMMENDATION PACKAGES - Cache Hit - {}", CacheKey.RECOMMENDATION_LLIFE_PACKAGES);
            return packages;
        }else{
            log.info("[REDIS] RECOMMENDATION PACKAGES - Cache Miss - {}", CacheKey.RECOMMENDATION_LLIFE_PACKAGES);
            packages = premiumDao.selectRecommendationPackages();
            cachePackages(CacheKey.RECOMMENDATION_LLIFE_PACKAGES, packages);
            return packages;
        }
    }

    @Override
    public PackageDetailVo getPremiumPackageDetail(int lfPackageId) {
        return premiumDao.selectPackageDetail(lfPackageId);
    }


    private List<PackageVo> getCachedSearchResult(String cacheKey) {
        @SuppressWarnings("unchecked")
        List<PackageVo> cachedData = (List<PackageVo>) redisTemplate.opsForValue().get(cacheKey);
        return cachedData;
    }


    private void cachePackages(String cacheKey, List<PackageVo> cachingData) {
        redisTemplate.opsForValue().set(cacheKey,cachingData, 1, TimeUnit.DAYS);  // 하루동안 캐싱
        log.info("[REDIS] 패키지 - Cache 저장 - {}", cacheKey);
    }
    @Override
    public List<ReviewVo> getReviews(int lfId) {

        return premiumDao.selectAllReviews(lfId);

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
            premiumDao.insertReview(reviewRequestVo);
            int insertedReviewId = reviewRequestVo.getLfReviewId();

            log.info("[리뷰 등록 ] 데이터 삽입 성공 {}", insertedReviewId);

            List<ReviewImgVo> reviewImgList = new ArrayList<>();

            if(fileNameList.size() !=0){
                for( String reviewImgUrl : fileNameList){
                    reviewImgList.add(ReviewImgVo.builder().rImgUrl(reviewImgUrl).lfReviewId(insertedReviewId).build());
                }
                premiumDao.insertReviewImg(reviewImgList);
            }
            ReviewVo reviewVo = premiumDao.selectReviewById(insertedReviewId);
            return reviewVo;
        }catch(Exception e) {
            log.info("[리뷰 등록 ] 데이터 삽입 오류 {} ", e.getStackTrace().toString());
            throw new Exception("데이터 삽입 오류");
        }
    }

    @Override
    public int deleteReview(int lfReviewId) {
        try{
            return premiumDao.deleteReview(lfReviewId);
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