package kosa.com.suntofu.L_LIFE.premium.service;


import kosa.com.suntofu.L_LIFE.premium.dao.PremiumDao;
import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PremiumServiceImpl implements PremiumService{

    private final PremiumDao premiumDao;

    @Override
    public List<PremiumVo> selectPremiumProductList() {
        return premiumDao.selectPremiumProductList();
    }

    @Override
    public int selectProductCountPagination(PaginationVo paginationVo) {
        return premiumDao.selectProductCountPagination(paginationVo);
    }
}
