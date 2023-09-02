package kosa.com.suntofu.L_LIFE.member.controller;

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
public class MemberRestController {
    private final MemberService memberService;


    @GetMapping("/{memberId}/mypage/standard/recent")
    public ResponseEntity<List<SubscriptionListVo>> recentStandardSubscription(
            @PathVariable int memberId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // 최근 스탠다드 구독내역
        List<SubscriptionListVo> recentStandardSubscriptionList  = memberService.getRecentStandardScriptionList(memberId,startDate,endDate);
        System.out.println(recentStandardSubscriptionList);
        return new ResponseEntity<>(recentStandardSubscriptionList, HttpStatus.OK);
    }

    @GetMapping("/{memberId}/mypage/premium/recent")
    public ResponseEntity<List<SubscriptionListVo>> recentPremiumSubscription(
            @PathVariable int memberId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // 최근 프리미엄 구독내역
        List<SubscriptionListVo> recentPremiumSubscriptionList  = memberService.getRecentPremiumScriptionList(memberId,startDate,endDate);
        System.out.println(recentPremiumSubscriptionList);
        return new ResponseEntity<>(recentPremiumSubscriptionList, HttpStatus.OK);
    }

    @PostMapping("/return/{productId}")
    public ResponseEntity<Integer> returnFurniture(@PathVariable int productId){
        System.out.println("반납 컨트롤러");
        memberService.updateSubcriptionStatus(productId);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
