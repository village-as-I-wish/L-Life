package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.vo.*;

import java.util.List;

public interface MemberService {
    MemberVo insertOrSelectMember(MemberVo memberVo);
    List<MemberVo> getAllProducts();

    List<CartVo> getAllStandardCarts(int memberId);

    List<CartVo> getAllPremiumCarts(int memberId);

    Integer getCurrentCoin(int memberId);

    List<SubscriptionListVo> getAllStandardScriptionList(int memberId);

    List<SubscriptionListVo> getRecentStandardScriptionList(int memberId, String startDate, String endDate);

    Integer getOrderCount(int memberId);

    List<DeliveryStatusVo> getDeliveryStatus(int memberId);

    List<DeliveryListVo> getDeliveryList(int memberId);

    void updateSubcriptionStatus(int productId);

    List<SubscriptionListVo> getAllpremiumScriptionList(int memberId);

    List<SubscriptionListVo> getRecentPremiumScriptionList(int memberId, String startDate, String endDate);
}
