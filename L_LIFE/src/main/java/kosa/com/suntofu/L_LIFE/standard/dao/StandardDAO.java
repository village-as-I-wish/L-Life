package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.standard.vo.StandardLiveVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StandardDAO {
    List<StandardVo> selectAllStandard();
    List<StandardLiveVo> selectAllLiveStream();
    List<StandardVo> selectStandardCategory(int fCategoryId);
    List<StandardVo> searchStandardProductByKeyword(String keyword);
}

