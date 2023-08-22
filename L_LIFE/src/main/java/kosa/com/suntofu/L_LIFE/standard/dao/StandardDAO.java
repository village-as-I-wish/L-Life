package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.standard.vo.StandardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StandardDAO {
    List<StandardVO> selectAllStandard();
}
