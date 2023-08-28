package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import java.util.List;

public interface MemberService {
    MemberVo insertOrSelectMember(MemberVo memberVo);
    List<MemberVo> getAllProducts();
}
