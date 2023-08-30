package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.dao.MemberDao;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import kosa.com.suntofu.L_LIFE.member.vo.CartVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public MemberVo insertOrSelectMember(MemberVo memberVo) {
        MemberVo existingMember = memberDao.selectMemberByEmail(memberVo.getMEmail());

        if (existingMember == null) {
            // 이메일로 검색한 멤버가 존재하지 않으면 새로운 멤버를 추가합니다.
            memberDao.insertMember(memberVo);
            return memberVo;
        } else {
            // 이메일로 검색한 멤버가 이미 존재하면 해당 멤버를 반환합니다.
            return existingMember;
        }
    }

    public List<MemberVo> getAllProducts() {

        return memberDao.findAllProducts();
    }

    @Override
    public List<CartVo> getAllStandardCarts(int memberId) {
        return memberDao.findAllStandardCarts(memberId);
    }

    @Override
    public List<CartVo> getAllPremiumCarts(int memberId) {
        return memberDao.findAllPremiumCarts(memberId);
    }
}

