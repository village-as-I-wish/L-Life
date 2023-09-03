package kosa.com.suntofu.L_LIFE.premium.service;

import kosa.com.suntofu.L_LIFE.premium.vo.PackageVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumDetailVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;

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
    PremiumVo selectPremiumProductDetailById(int lfId);

    List<PackageVo> getMDPickPackages();
    List<PackageVo> getPromotionPackages();
}