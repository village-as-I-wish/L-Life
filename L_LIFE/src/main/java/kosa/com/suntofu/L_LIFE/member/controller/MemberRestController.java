package kosa.com.suntofu.L_LIFE.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.SubscriptionListVo;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "회원 API")

public class MemberRestController {
    private final MemberService memberService;

    @Operation(summary = "최근 스탠다드 가구 구독 내역", description = "최근 스탠다드 가구 구독 내역")
    @GetMapping("/{memberId}/mypage/standard/recent")
    public ResponseEntity<List<SubscriptionListVo>> recentStandardSubscription(
            @PathVariable int memberId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // 최근 스탠다드 구독내역
        List<SubscriptionListVo> recentStandardSubscriptionList  = memberService.getRecentStandardScriptionList(memberId,startDate,endDate);
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
    public ResponseEntity<Integer> returnFurniture(@PathVariable int productId){
        memberService.updateSubcriptionStatus(productId);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
