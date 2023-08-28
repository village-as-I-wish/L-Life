package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loadLoginPage(){ return "pages/member/login"; }

    @PostMapping("/login")
    public String kakaoLogin(@RequestParam String email, @RequestParam String name, @RequestParam String gender, @RequestParam String profile){
        MemberVo memberVo = new MemberVo(1,name,gender,0,0,"",email,profile,"");
        MemberVo existingMember = memberService.insertOrSelectMember(memberVo);
        return "pages/main/main";
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
