package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.ArrayList;

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
    public String loadMyPageStandard(Model model){

        List<MemberVo> products = new ArrayList<>();
        products.add(new MemberVo("2023-08-20", "product1.jpeg", "제품1", 3, "월33,000"));
        products.add(new MemberVo("2023-08-20", "product2.jpeg", "제품2", 5, "월33,000"));
        model.addAttribute("products", products);

        return "pages/member/mypage_standard";
    }

    @GetMapping("/mypage/{memberId}/premium")
    public String loadMyPagePremium(Model model){
        return "pages/member/mypage_premium";
    }
}