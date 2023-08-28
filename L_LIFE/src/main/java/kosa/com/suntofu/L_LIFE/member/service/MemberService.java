package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;

public interface MemberService {

    MemberVo insertOrSelectMember(MemberVo memberVo);
}
