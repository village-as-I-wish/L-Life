package kosa.com.suntofu.L_LIFE.subscription.controller;


import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import kosa.com.suntofu.L_LIFE.subscription.vo.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionRestController {

    private final SubscriptionService subscriptionService;

    @PostMapping("")
    public ResponseEntity<BasicResponse> subscribePlan(int subscriptionPlanId, @SessionAttribute int memberId){
        log.info("[구독 가입 ] 구독 플랜 타입 번호 :  {} ", subscriptionPlanId);
        subscriptionService.subscribePlan(subscriptionPlanId, memberId);
        log.info("[구독 가입 ] 구독 플랜 가입 완료");

        ResponseEntity responseEntity = new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("fdkdf").build(), HttpStatus.ACCEPTED);
        return responseEntity;
    }

}
