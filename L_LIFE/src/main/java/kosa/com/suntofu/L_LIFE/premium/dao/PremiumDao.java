package kosa.com.suntofu.L_LIFE.premium.dao;


import kosa.com.suntofu.L_LIFE.premium.vo.PackageVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumDetailVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PremiumDao {
    List<PremiumVo> selectPremiumProductList(PaginationVo paginationVo);
    int selectProductCountPagination(PaginationVo paginationVo);
    List<PremiumVo> selectProductByCategory(PaginationVo paginationVo);
    int selectProductCountByCategoryByPagination(PaginationVo paginationVo);
    List<PremiumVo> selectProductByKeyword(PaginationVo paginationVo);
    int selectProductByKeywordPagination(PaginationVo paginationVo);
    PremiumVo selectPremiumProductDetailById(int lfId);

    List<PackageVo> selectMDPickPackages();
    List<PackageVo> selectPromotionPackages();
}
