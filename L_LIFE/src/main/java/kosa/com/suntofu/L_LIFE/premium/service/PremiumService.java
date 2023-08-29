package kosa.com.suntofu.L_LIFE.premium.service;

import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;

import java.util.List;

public interface PremiumService {

    List<PremiumVo> selectPremiumProductList();
    int selectProductCountPagination (PaginationVo paginationVo);
}
