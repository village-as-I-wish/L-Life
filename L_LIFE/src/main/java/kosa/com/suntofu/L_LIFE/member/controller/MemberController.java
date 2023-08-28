package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loadLoginPage(){ return "pages/member/login"; }

    @RequestMapping(value="/kakaoLogin", method= RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
        System.out.println("#########" + code);
        String access_Token = memberService.getAccessToken(code);

        // userInfo의 타입을 KakaoDTO로 변경 및 import.
        MemberVo userInfo = memberService.getUserInfo(access_Token);

        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###email#### : " + userInfo.get("email"));
        return "pages/member/testPage";
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
