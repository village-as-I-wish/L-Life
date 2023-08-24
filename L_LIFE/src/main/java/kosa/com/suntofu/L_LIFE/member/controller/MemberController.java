package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loadLoginPage(){
        return "pages/member/login";
    }
    @GetMapping("/mypage")
    public String loadMyPage(Model model){
        return "pages/member/mypage";
    }
    @GetMapping("/mypage/{memberId}/standard")
    public String loadStandardMyPage(Model model){
        return "pages/member/standard_mypage";
    }

    @GetMapping("/mypage/{memberId}/premium")
    public String loadPremiumMyPage(Model model){
        return "pages/member/premium_mypage";
    }
}
