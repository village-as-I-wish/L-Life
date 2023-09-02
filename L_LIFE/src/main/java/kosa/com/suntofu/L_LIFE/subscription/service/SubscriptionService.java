package kosa.com.suntofu.L_LIFE.subscription.service;

import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionPlanVo;

public interface SubscriptionService {
    BillVo getSubscriptionPayBill(Integer subscriptionPlanId);
    int subscribePlan(Integer subscriptionPlanId, Integer memberId);

}
