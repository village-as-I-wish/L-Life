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
}
