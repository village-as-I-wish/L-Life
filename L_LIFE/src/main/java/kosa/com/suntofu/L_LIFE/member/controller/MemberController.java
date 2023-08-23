package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public String loadMyPage(Model model){
        return "pages/mypage/mypage";
    }
    @GetMapping("/{memberId}/standard")
    public String loadStandardMyPage(Model model){
        return "pages/mypage/standard_mypage";
    }

    @GetMapping("/{memberId}/premium")
    public String loadPremiumMyPage(Model model){
        return "pages/mypage/premium_mypage";
    }
}
