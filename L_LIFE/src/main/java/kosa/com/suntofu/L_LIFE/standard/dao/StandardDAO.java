package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.standard.vo.SearchRequestVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardLiveVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StandardDAO {
    List<StandardVo> selectAllStandard();
    List<StandardLiveVo> selectAllLiveStream();
    List<StandardVo> selectStandardProductByCategory(int fCategoryId);
    List<StandardVo> selectStandardProductByKeyword(String keyword);
    List<StandardVo> searchStandardProductByFilter(SearchRequestVo requestVo);
    }

