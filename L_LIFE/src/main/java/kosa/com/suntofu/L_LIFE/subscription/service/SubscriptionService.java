package kosa.com.suntofu.L_LIFE.subscription.service;

import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayFurnitureVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionPlanVo;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface SubscriptionService {
    BillVo getSubscriptionPayBill(Integer subscriptionPlanId);
    int subscribePlan(Integer subscriptionPlanId, Integer memberId);

    int addPrLFSubcriptoin(List<PayFurnitureVo> payFurnitureList);

    int addStLFSubcriptoin(List<PayFurnitureVo> payFurnitureList);

    @Scheduled
    int renewStSubscription();

    int subscribePremiumPlan(int mId);

}
