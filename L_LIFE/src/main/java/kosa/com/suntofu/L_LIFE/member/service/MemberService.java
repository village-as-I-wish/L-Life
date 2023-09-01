package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.vo.*;

import java.util.List;

public interface MemberService {
    MemberVo insertOrSelectMember(MemberVo memberVo);
    List<MemberVo> getAllProducts();

    List<CartVo> getAllStandardCarts(int memberId);

    List<CartVo> getAllPremiumCarts(int memberId);

    int getCurrentCoin(int mId);

    List<SubscriptionListVo> getAllStandardScriptionList(int memberId);

    List<SubscriptionListVo> getRecentStandardScriptionList(int memberId, String startDate, String endDate);

    int getOrderCount(int mId);

    List<DeliveryStatusVo> getDeliveryStatus(int mId);

    List<DeliveryListVo> getDeliveryList(int memberId);
}
