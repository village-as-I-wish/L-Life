package kosa.com.suntofu.L_LIFE.subscription.service;


import kosa.com.suntofu.L_LIFE.subscription.dao.SubscriptionDao;
import kosa.com.suntofu.L_LIFE.subscription.util.SubscriptionPlanType;
import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionPlanVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    @Override
    public BillVo getSubscriptionPayBill(Integer subScriptionPlanType) {

        if(subScriptionPlanType == SubscriptionPlanType.Standard33.getType()){ // 33 구독
                SubscriptionPlanVo subscriptionPlanVo = SubscriptionPlanVo.builder()
                        .subscriptionPlanType(0)
                        .subscriptionPlanName(SubscriptionPlanType.Standard33.getName())
                        .subscriptionStartDate(LocalDate.now())
                        .subscriptionFinDate(LocalDate.now().plusMonths(1))
                        .subscriptionPlanPrice(SubscriptionPlanType.Standard33.getPrice()).build();

                return new BillVo( 0 , subscriptionPlanVo, SubscriptionPlanType.Standard33.getPrice());

        }
        if(subScriptionPlanType == SubscriptionPlanType.Standard55.getType()){ // 33 구독
                SubscriptionPlanVo subscriptionPlanVo = SubscriptionPlanVo.builder()
                        .subscriptionPlanType(0)
                        .subscriptionPlanName(SubscriptionPlanType.Standard55.getName())
                        .subscriptionStartDate(LocalDate.now())
                        .subscriptionFinDate(LocalDate.now().plusMonths(1))
                        .subscriptionPlanPrice(SubscriptionPlanType.Standard55.getPrice()).build();

                return new BillVo( 0 , subscriptionPlanVo, SubscriptionPlanType.Standard55.getPrice());
        }
        return new BillVo();
    }
}
