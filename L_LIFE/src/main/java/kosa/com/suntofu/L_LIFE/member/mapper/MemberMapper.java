package kosa.com.suntofu.L_LIFE.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    void insertOrGetMember(Map<String, Object> params);
}
