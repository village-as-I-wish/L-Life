package kosa.com.suntofu.L_LIFE.premium.service;


import kosa.com.suntofu.L_LIFE.constant.CacheKey;
import kosa.com.suntofu.L_LIFE.premium.dao.PremiumDao;
import kosa.com.suntofu.L_LIFE.premium.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PremiumServiceImpl implements PremiumService{

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
        return (int)Math.ceil((double)totalNum / 16);
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
    public int selectProductByFilterByPagination(PaginationVo paginationVo) {
        return premiumDao.selectProductByFilterByPagination(paginationVo);
    }


    @Override
    public PremiumVo selectPremiumProductDetailById(int lfId) {
        return premiumDao.selectPremiumProductDetailById(lfId);
    }

    @Override
    public List<PremiumVo> selectProductImgById(int lfId) {
        return premiumDao.selectProductImgById(lfId);
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
    public List<PremiumVo> selectPremiumRecommendation(int lfId) {
        return premiumDao.selectPremiumRecommendation(lfId);
    }

    @Override
    public int insertPremiumProductToCart(PremiumOptionVo premiumOptionVo) {
        return premiumDao.insertPremiumProductToCart(premiumOptionVo);
    }

    @Override
    public List<PackageVo> getMDPickPackages() {
        List<PackageVo> packages = getCachedSearchResult(CacheKey.MDPICK_LLIFE_PACKAGES);
        if (packages != null){
            log.info("[REDIS] SEARCH - Cache Hit - {}", CacheKey.MDPICK_LLIFE_PACKAGES);
            return packages;
        }else{
            log.info("[REDIS] TOP_10 - Cache Miss - {}", CacheKey.MDPICK_LLIFE_PACKAGES);
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
        return premiumDao.selectPromotionPackages();
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
        redisTemplate.opsForValue().set(cacheKey,cachingData, 1, TimeUnit.DAYS);
        log.info("[REDIS] 패키지 - Cache 저장 - {}", cacheKey);
    }

}