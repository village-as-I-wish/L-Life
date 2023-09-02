package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.standard.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StandardDAO {
    List<StandardVo> selectAllStandard();
    List<StandardLiveVo> selectAllLiveStream();
    List<StandardVo> selectStandardProductByCategory(int fCategoryId);
    List<StandardVo> selectStandardProductByKeyword(String keyword);
    List<StandardVo> searchStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo selectStandardDetailById(int lfId);
    List<StandardOptionVo> selectStandardOptionById(int lfId);
    List<StandardRefurVo> selectStandardRefurById(int lfId);
    List<StandardVo> selectStandardRecommendation(int lfId);
    List<StandardRestockVo> selectStandardStockAmount(int lfId);
}

