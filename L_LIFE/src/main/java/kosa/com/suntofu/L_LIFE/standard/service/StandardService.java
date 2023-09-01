package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.standard.vo.SearchRequestVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardDetailVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardLiveVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;

import java.util.List;

public interface StandardService {
    List<StandardVo> getAllStandard();
    List<StandardLiveVo> getAllLiveStream();
    List<StandardVo> getStandardByCategory(int fCategoryId);
    List<StandardVo> getStandardProductByKeyword(String keyword);
    List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo getStandardDetailById(int lfId);
}
