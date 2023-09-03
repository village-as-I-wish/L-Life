package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.standard.vo.*;

import java.util.List;

public interface StandardService {
    List<StandardVo> getAllStandard();
    List<StandardLiveVo> getAllLiveStream();
    List<StandardVo> getStandardByCategory(int fCategoryId);
    List<StandardVo> getStandardProductByKeyword(String keyword);
    List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo getStandardDetailById(int lfId);
    List<StandardOptionVo> getStandardOptionById(int lfId);
    List<StandardRefurVo> getStandardRefurById(int lfId);
    List<StandardVo> getStandardRecommendation(int lfId);
    int getStandardStockAmount(int lfOptId, int lfId);
}
