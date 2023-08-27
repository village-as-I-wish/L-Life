package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loadLoginPage(){ return "pages/member/login"; }

    //redirect 경로 mapping
    @RequestMapping("/login/kakao")
    public String kakaoLogin(@RequestParam(value = "code",required = false) String code){
        if(code!=null){//카카오측에서 보내준 code가 있다면 출력합니다
            System.out.println("여기 도착했습티");
            System.out.println("code = " + code);
        }
        return null; //만들어둔 응답받을 View 페이지 redirectPage.html 리턴
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
