package kosa.com.suntofu.L_LIFE.subscription.service;

import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;

public interface SubscriptionService {
    BillVo getSubscriptionPayBill(Integer subscriptionPlanType);
}
