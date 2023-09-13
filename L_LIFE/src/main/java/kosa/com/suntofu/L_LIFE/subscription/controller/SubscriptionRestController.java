package kosa.com.suntofu.L_LIFE.subscription.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import kosa.com.suntofu.L_LIFE.subscription.util.SubscriptionReturn;
import kosa.com.suntofu.L_LIFE.subscription.vo.BasicResponse;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayFurnitureVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
@Tag(name = "subscription", description = "구독 API")
public class SubscriptionRestController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "스탠다드 구독권 구매", description = "스탠다드 구독권을 구매합니다(33/55)")
    @PostMapping("")
    public ResponseEntity<BasicResponse> subscribePlan(int subscriptionPlanId){ //세션에서 memberId 가져올 예정
        int memberId = 1; // MemerId 변경 필요
        int result = subscriptionService.subscribePlan(subscriptionPlanId, memberId);
        if(result == SubscriptionReturn.SUBSCRIPTION_SUCCESS){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("구독이 완료되었습니다.").result(1).build(), HttpStatus.OK);
        }else if(result == SubscriptionReturn.SUBSCRIPTION_ALREADY_EXISTS){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("이미 구독중인 상태입니다.").result(-1).build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("[구독 플랜 ] - 데이터 처리 서버 오류 발생 ").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "프리미엄 가구 구독", description = "프리미엄 가구 구독")
    @PostMapping("premium")
    public ResponseEntity<BasicResponse> subscribePremium(HttpServletRequest request,
                                                          @RequestParam String checkedDay,
                                                          @RequestParam String checkedTime){ //세션에서 memberId 가져올 예정
        HttpSession session = request.getSession();
        List<PayFurnitureVo> payFurnitureList = (List<PayFurnitureVo>) session.getAttribute("prPaymentProduct");
        log.info("premium payment {}",payFurnitureList);

        for (PayFurnitureVo payFurnitureVo: payFurnitureList){
            payFurnitureVo.setDeliveryDate(checkedDay);
            payFurnitureVo.setDeliveryTime(checkedTime);
        }

        int result = subscriptionService.addPrLFSubcriptoin(payFurnitureList);
        session.removeAttribute("prPaymentProduct");


        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("프리미엄 구독이 완료되었습니다.").result(1).build(), HttpStatus.OK);

    }
    @Operation(summary = "스탠다드 가구 구독", description = "스탠다드 가구 구독")
    @PostMapping("standard")
    public ResponseEntity<BasicResponse> subscribeStandard(HttpServletRequest request,
                                                           @RequestParam String checkedDay,
                                                           @RequestParam String checkedTime,
                                                           @RequestParam String deliveryAddress){
        HttpSession session = request.getSession();
        List<PayFurnitureVo> payFurnitureList = (List<PayFurnitureVo>) session.getAttribute("stPaymentProduct");
        log.info("starndard {}",payFurnitureList);
        log.info("starndard day{}",checkedDay);
        log.info("starndard time{}",checkedTime);
        log.info("deliveryAddress {}",deliveryAddress);

        for (PayFurnitureVo payFurnitureVo: payFurnitureList){
            payFurnitureVo.setDeliveryDate(checkedDay);
            payFurnitureVo.setDeliveryTime(checkedTime);
            payFurnitureVo.setDeliveryAddress(deliveryAddress);
        }

        int result = subscriptionService.addStLFSubcriptoin(payFurnitureList);

        session.removeAttribute("stPaymentProduct");

        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("스탠다드 가구 구독이 완료되었습니다.").result(1).build(), HttpStatus.OK);

    }

}
