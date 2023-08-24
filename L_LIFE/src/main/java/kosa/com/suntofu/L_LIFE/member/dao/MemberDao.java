package kosa.com.suntofu.L_LIFE.member.dao;


import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {

    List<MemberVo> findAllProducts();
}
