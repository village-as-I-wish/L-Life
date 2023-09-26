package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.vo.*;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;

import java.util.List;

public interface MemberService {
    MemberVo insertOrSelectMember(MemberVo memberVo);
    MemberVo selectMemberById(int memberId);
    List<MemberVo> getAllProducts();

    List<CartVo> getAllStandardCarts(int memberId);

    List<CartVo> getAllPremiumCarts(int memberId);

    Integer getCurrentCoin(int memberId);

    List<SubscriptionListVo> getAllStandardScriptionList(int memberId);

    List<SubscriptionListVo> getRecentStandardScriptionList(int memberId, String startDate, String endDate);

    Integer getOrderCount(int memberId);

    int getDeliveryReadyStatus(int memberId);
    int getDeliveryProgressStatus(int mId);
    int getDeliveryCompleteStatus(int mId);


    List<DeliveryListVo> getDeliveryList(int memberId);

    void updateSubcriptionStatus(int productId);

    List<SubscriptionListVo> getAllpremiumScriptionList(int memberId);

    List<SubscriptionListVo> getRecentPremiumScriptionList(int memberId, String startDate, String endDate);

    Integer getStandardSubscriptionId(int mId);

    Integer getPremiumSubscriptionId(int mId);

    SubscriptionVo getStandardSubscription(int memberId);

    SubscriptionVo getPremiumSubscription(int memberId);
}
