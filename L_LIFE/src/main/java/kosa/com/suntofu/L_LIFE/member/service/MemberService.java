package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import kosa.com.suntofu.L_LIFE.member.vo.CartVo;
import kosa.com.suntofu.L_LIFE.member.vo.SubscriptionListVo;

import java.util.List;

public interface MemberService {
    MemberVo insertOrSelectMember(MemberVo memberVo);
    List<MemberVo> getAllProducts();

    List<CartVo> getAllStandardCarts(int memberId);

    List<CartVo> getAllPremiumCarts(int memberId);

    int getCurrentCoin(int mId);

    List<SubscriptionListVo> getAllStandardScriptionList(int memberId);

    List<SubscriptionListVo> getRecentStandardScriptionList(int memberId, String startDate, String endDate);
}
