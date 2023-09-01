package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.SubscriptionListVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
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

}
