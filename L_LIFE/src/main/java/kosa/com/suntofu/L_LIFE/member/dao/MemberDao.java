package kosa.com.suntofu.L_LIFE.member.dao;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@Mapper
public interface MemberDao {

    @Autowired
    private SqlSessionTemplate sql;

    // 정보 저장
    public void kakaoinsert(HashMap<String, Object> userInfo) {
        sql.insert("Member.kakaoInsert",userInfo);
    }

    // 정보 확인
    public KakaoDTO findkakao(HashMap<String, Object> userInfo) {
        System.out.println("RN:"+userInfo.get("nickname"));
        System.out.println("RE:"+userInfo.get("email"));
        return sql.selectOne("Member.findKakao", userInfo);
    }
}
