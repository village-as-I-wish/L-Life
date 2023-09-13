package kosa.com.suntofu.L_LIFE.premium.service;

import kosa.com.suntofu.L_LIFE.premium.vo.*;

import java.util.List;

public interface PremiumService {

    List<PremiumVo> selectPremiumProductList(PaginationVo paginationVo);
    int selectProductCountPagination (PaginationVo paginationVo);
    PaginationVo calculateAndSetOffset(PaginationVo paginationVo);
    int calculatePaginationNum(int totalNum);
    List<PremiumVo> selectProductByCategory(PaginationVo paginationVo);
    int selectProductCountByCategoryByPagination (PaginationVo paginationVo);
    List<PremiumVo> selectProductByKeyword(PaginationVo paginationVo);
    int selectProductByKeywordPagination(PaginationVo paginationVo);
    List<PremiumVo> selectProductByFilter(PaginationVo paginationVo);
    PremiumVo selectPremiumProductDetailById(int lfId);
    List<PremiumOptionVo> selectPremiumOptionById(int lfId);
    int selectPremiumStockAmount(int lfOptId, int lfId);
    int insertOptionToReservation(PremiumOptionVo premiumOptionVo);
    List<PremiumVo> selectPremiumRecommendation(PremiumVo lfId);
    int insertPremiumProductToCart(PremiumOptionVo premiumOptionVo);
    List<PackageVo> getMDPickPackages();
    List<PackageVo> getPromotionPackages();

    PackageDetailVo getPremiumPackageDetail(int lfPackageId);

    List<PackageVo> getRecommendationPackages();
}