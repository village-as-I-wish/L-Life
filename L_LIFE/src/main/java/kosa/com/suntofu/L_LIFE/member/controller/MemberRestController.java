package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.SubscriptionListVo;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberRestController {
    private final MemberService memberService;


    @GetMapping("/{memberId}/mypage/standard/recent")
    public String recentStandardSubscription(@PathVariable int memberId,
                                             @RequestParam String startDate,
                                             @RequestParam String endDate,
                                             Model model){
        // 최근 스탠다드 구독내역
        List<SubscriptionListVo> recentStandardSubscriptionList  = memberService.getRecentStandardScriptionList(memberId,startDate,endDate);

        model.addAttribute("recentStandardSubscriptionList",recentStandardSubscriptionList);
        return "pages/member/mypage_standard_recent_subscription_table :: standardRecentSubscriptionTable";
    }

    @PostMapping("/return/{productId}")
    public ResponseEntity<Integer> returnFurniture(@PathVariable int productId){
        System.out.println("반납 컨트롤러");
        memberService.updateSubcriptionStatus(productId);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
