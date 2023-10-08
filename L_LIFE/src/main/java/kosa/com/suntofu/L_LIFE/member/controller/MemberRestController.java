package kosa.com.suntofu.L_LIFE.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.common.vo.BasicResponse;
import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.SubscriptionListVo;
import kosa.com.suntofu.L_LIFE.notification.service.NotificationService;
import kosa.com.suntofu.L_LIFE.notification.vo.NotificationMessageVo;
import kosa.com.suntofu.L_LIFE.notification.vo.RestockVo;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "회원 API")

public class MemberRestController {
    private final MemberService memberService;
    private final NotificationService notificationService;
    private final TemplateEngine templateEngine;

    @Operation(summary = "최근 스탠다드 가구 구독 내역", description = "최근 스탠다드 가구 구독 내역")
    @GetMapping("/{memberId}/mypage/standard/recent")
    public ResponseEntity<List<SubscriptionListVo>> recentStandardSubscription(
            @PathVariable int memberId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // 최근 스탠다드 구독내역
        List<SubscriptionListVo> recentStandardSubscriptionList  = memberService.getRecentStandardScriptionList(memberId,startDate,endDate);
        log.info("recent st: {}", recentStandardSubscriptionList);
        return new ResponseEntity<>(recentStandardSubscriptionList, HttpStatus.OK);
    }


    @Operation(summary = "최근 프리미엄 가구 구독 내역", description = "최근 프리미엄 가구 구독 내역")
    @GetMapping("/{memberId}/mypage/premium/recent")
    public ResponseEntity<List<SubscriptionListVo>> recentPremiumSubscription(
            @PathVariable int memberId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // 최근 프리미엄 구독내역
        List<SubscriptionListVo> recentPremiumSubscriptionList  = memberService.getRecentPremiumScriptionList(memberId,startDate,endDate);
        return new ResponseEntity<>(recentPremiumSubscriptionList, HttpStatus.OK);
    }

    @Operation(summary = "가구 반납", description = "가구 반납")
    @PostMapping("/return/{productId}")
    public ResponseEntity<BasicResponse> returnFurniture(@PathVariable int productId,
                                                         @RequestBody RestockVo restockVo){
        memberService.updateSubcriptionStatus(productId);

        Context context = new Context();
        context.setVariable("furniture", restockVo);
        String message = templateEngine.process("pages/notification/mail_alert", context);

        NotificationMessageVo notificationMessageVo = new NotificationMessageVo("hyewon5266@naver.com", "재입고 알림", message);
        notificationService.sendMail(notificationMessageVo);
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("재입고 메일 발송이 완료되었습니다.").result(1).build(), HttpStatus.OK);

    }
}
