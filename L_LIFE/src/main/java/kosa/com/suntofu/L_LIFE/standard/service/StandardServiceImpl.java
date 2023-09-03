package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.standard.dao.StandardDAO;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService {

    private final StandardDAO standardDAO;

    @Override
    public List<StandardVo> getAllStandard() {

        return standardDAO.selectAllStandard();
    }

    @Override
    public List<StandardLiveVo> getAllLiveStream() {

        return standardDAO.selectAllLiveStream();
    }

    @Override
    public List<StandardVo> getStandardByCategory(int fCategoryId) {

        return standardDAO.selectStandardProductByCategory(fCategoryId);
    }

    @Override
    public List<StandardVo> getStandardProductByKeyword(String keyword) {

        return standardDAO.selectStandardProductByKeyword(keyword);
    }

    @Override
    public List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo) {

        return standardDAO.searchStandardProductByFilter(requestVo);
    }

    @Override
    public StandardDetailVo getStandardDetailById(int lfId) {

        return standardDAO.selectStandardDetailById(lfId);
    }

    @Override
    public List<StandardOptionVo> getStandardOptionById(int lfId) {

        return standardDAO.selectStandardOptionById(lfId);
    }

    @Override
    public List<StandardRefurVo> getStandardRefurById(int lfId) {

        return standardDAO.selectStandardRefurById(lfId);
    }


    @Override
    public List<StandardVo> getStandardRecommendation(int lfId) {

        return standardDAO.selectStandardRecommendation(lfId);
    }

    @Override
    public int getStandardStockAmount(int lfOptId, int lfId) {

        StandardRestockVo restock = standardDAO.selectStandardStockAmount(lfOptId, lfId);
        if (restock != null) {
            int stockAmount = restock.getStockAmount();
            return stockAmount;
        }
        return -1;
    }

}



