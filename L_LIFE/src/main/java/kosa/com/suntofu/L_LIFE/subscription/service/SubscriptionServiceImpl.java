package kosa.com.suntofu.L_LIFE.subscription.service;


import kosa.com.suntofu.L_LIFE.subscription.dao.SubscriptionDao;
import kosa.com.suntofu.L_LIFE.subscription.util.SubscriptionReturn;
import kosa.com.suntofu.L_LIFE.subscription.util.SubscriptionPlan;
import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayFurnitureVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionPlanVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    @Override
    public BillVo getSubscriptionPayBill(Integer subScriptionPlanId) {

        if(subScriptionPlanId == SubscriptionPlan.Standard33.getId()){ // 33 구독
                SubscriptionPlanVo subscriptionPlanVo = SubscriptionPlanVo.builder()
                        .subscriptionPlanId(SubscriptionPlan.Standard33.getId())
                        .subscriptionPlanName(SubscriptionPlan.Standard33.getName())
                        .subscriptionStartDate(LocalDate.now())
                        .subscriptionFinDate(LocalDate.now().plusMonths(1))
                        .subscriptionPlanPrice(SubscriptionPlan.Standard33.getPrice()).build();
                return new BillVo( 0 , subscriptionPlanVo, SubscriptionPlan.Standard33.getPrice());

        }
        if(subScriptionPlanId == SubscriptionPlan.Standard55.getId()){ // 55 구독
                SubscriptionPlanVo subscriptionPlanVo = SubscriptionPlanVo.builder()
                        .subscriptionPlanId(SubscriptionPlan.Standard55.getId())
                        .subscriptionPlanName(SubscriptionPlan.Standard55.getName())
                        .subscriptionStartDate(LocalDate.now())
                        .subscriptionFinDate(LocalDate.now().plusMonths(1))
                        .subscriptionPlanPrice(SubscriptionPlan.Standard55.getPrice()).build();

                return new BillVo( 0 , subscriptionPlanVo, SubscriptionPlan.Standard55.getPrice());
        }
        return new BillVo();
    }

    @Override
    public int subscribePlan(Integer subscriptionPlanId, Integer mId){
        SubscriptionVo subscriptionVo = null;
        if(subscriptionPlanId == SubscriptionPlan.Standard33.getId()){
            subscriptionVo  = SubscriptionVo.builder().subscriptionType(0)
                    .mId(mId)
                    .subscriptionPlanId(subscriptionPlanId)
                    .subscriptionPoint(SubscriptionPlan.Standard33.getCoinNum())
                    .subscriptionStatus(1).build();
        }

        if(subscriptionPlanId == SubscriptionPlan.Standard55.getId()){
            subscriptionVo = SubscriptionVo.builder().subscriptionType(0)
                    .mId(mId)
                    .subscriptionPlanId(subscriptionPlanId)
                    .subscriptionPoint(SubscriptionPlan.Standard55.getCoinNum())
                    .subscriptionStatus(1).build();
        }
        log.info("[스탠다드 구독 가입 - 플랜 ] : {}",subscriptionVo);
        try{
            subscriptionDao.insertSubscription(subscriptionVo);
            return SubscriptionReturn.SUBSCRIPTION_SUCCESS;
        }catch(Exception e){
            if (e.getCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getCause();
                int oracleErrorCode = sqlException.getErrorCode();
                if (oracleErrorCode == 20001) {
                    log.info("[스탠다드 구독 가입 - 플랜 ] 이미 가입된 상태");
                    return SubscriptionReturn.SUBSCRIPTION_ALREADY_EXISTS;
                } else {
                    log.info("[스탠다드 구독 가입 - 플랜 ] 데이터 삽입 오류 발생 ");
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                    return SubscriptionReturn.SUBSCRIPTION_ERROR;
                }
            }
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());

            log.info("[스탠다드 구독 가입 - 플랜 ] 데이터 삽입 오류 발생 ");
            return SubscriptionReturn.SUBSCRIPTION_ERROR;
        }
    }

    @Transactional
    @Override
    public int addPrLFSubcriptoin(List<PayFurnitureVo> payFurnitureList) {

        for (PayFurnitureVo payFurniture : payFurnitureList){
            int res1 = subscriptionDao.updateStock(payFurniture);
            int res2 = subscriptionDao.insertPrLFSubscription(payFurniture);
            int res3 = subscriptionDao.insertdelivery(payFurniture);
            int res4 = subscriptionDao.deleteCart(payFurniture);
        }
        return 1;
    }

    @Transactional
    @Override
    public int addStLFSubcriptoin(List<PayFurnitureVo> payFurnitureList) {

        for (PayFurnitureVo payFurniture : payFurnitureList) {
            int res1 = subscriptionDao.stUpdateStock(payFurniture);
            int res2 = subscriptionDao.stInsertStLFSubscription(payFurniture);
            int res3 = subscriptionDao.stInsertDelivery(payFurniture);
            int res4 = subscriptionDao.stDeleteCart(payFurniture);
            int res5 = subscriptionDao.stUpdateSubPoint(payFurniture);

        }
        return 1;
    }

    @Override
//    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 실행
    @Scheduled(cron = "0 * * * * *") // 매 분 실행
    public int renewStSubscription() {
         subscriptionDao.renewStSubscription();
        return 1;
    }
}
