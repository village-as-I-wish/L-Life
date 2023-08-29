package kosa.com.suntofu.L_LIFE.premium.dao;


import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PremiumDao {
    List<PremiumVo> selectPremiumProductList();
    int selectProductCountPagination(PaginationVo paginationVo);
}
