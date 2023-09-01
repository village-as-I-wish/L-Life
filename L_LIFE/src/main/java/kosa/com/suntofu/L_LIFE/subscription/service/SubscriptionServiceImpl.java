package kosa.com.suntofu.L_LIFE.subscription.service;


import kosa.com.suntofu.L_LIFE.subscription.dao.SubscriptionDao;
import kosa.com.suntofu.L_LIFE.subscription.util.SubscriptionPlan;
import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionPlanVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    @Override
    public BillVo getSubscriptionPayBill(Integer subScriptionPlanType) {

        if(subScriptionPlanType == SubscriptionPlan.Standard33.getId()){ // 33 구독
                SubscriptionPlanVo subscriptionPlanVo = SubscriptionPlanVo.builder()
                        .subscriptionPlanType(0)
                        .subscriptionPlanName(SubscriptionPlan.Standard33.getName())
                        .subscriptionStartDate(LocalDate.now())
                        .subscriptionFinDate(LocalDate.now().plusMonths(1))
                        .subscriptionPlanPrice(SubscriptionPlan.Standard33.getPrice()).build();

                return new BillVo( 0 , subscriptionPlanVo, SubscriptionPlan.Standard33.getPrice());

        }
        if(subScriptionPlanType == SubscriptionPlan.Standard55.getId()){ // 33 구독
                SubscriptionPlanVo subscriptionPlanVo = SubscriptionPlanVo.builder()
                        .subscriptionPlanType(0)
                        .subscriptionPlanName(SubscriptionPlan.Standard55.getName())
                        .subscriptionStartDate(LocalDate.now())
                        .subscriptionFinDate(LocalDate.now().plusMonths(1))
                        .subscriptionPlanPrice(SubscriptionPlan.Standard55.getPrice()).build();

                return new BillVo( 0 , subscriptionPlanVo, SubscriptionPlan.Standard55.getPrice());
        }
        return new BillVo();
    }

    @Transactional
    @Override
    public Boolean subscribePlan(Integer subscriptionPlanId, Integer memberId){
        SubscriptionVo subscriptionVo = null;
        if(subscriptionPlanId == SubscriptionPlan.Standard33.getId()){
            subscriptionVo  = SubscriptionVo.builder().subscriptionType(0)
                    .memberId(memberId)
                    .subscriptionPlanId(subscriptionPlanId)
                    .subscriptionPoint(SubscriptionPlan.Standard33.getCoinNum())
                    .subscriptionStatus(1).build();
        }

        if(subscriptionPlanId == SubscriptionPlan.Standard55.getId()){
            subscriptionVo = SubscriptionVo.builder().subscriptionType(0)
                    .memberId(memberId)
                    .subscriptionPlanId(subscriptionPlanId)
                    .subscriptionPoint(SubscriptionPlan.Standard55.getCoinNum())
                    .subscriptionStatus(1).build();
        }
        try{
            if (subscriptionDao.insertSubscription(subscriptionVo) ==1 ){
                return true;
            }
                throw new Exception("스탠다드 구독 가입 오류입니다. ");
        }catch(Exception e){
            log.info("[스탠다드 구독 가입 - 플랜 ] 데이터 삽입 오류 발생 ");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return false;
        }
    }
}
