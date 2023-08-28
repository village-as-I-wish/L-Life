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
    @GetMapping("/{memberId}/mypage")
    public String loadMyPage(Model model){

        List<MemberVo> products = new ArrayList<>();
        products.add(new MemberVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new MemberVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_main";
    }

    @GetMapping("/{memberId}/mypage/standard")
    public String loadMyPageStandard(Model model){

        List<MemberVo> products = new ArrayList<>();
        products.add(new MemberVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new MemberVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);

        return "pages/member/mypage_standard";
    }

    @GetMapping("/{memberId}/mypage/premium")
    public String loadMyPagePremium(Model model){

        List<MemberVo> products = new ArrayList<>();
        products.add(new MemberVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new MemberVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_premium";
    }

    @GetMapping("/{memberId}/mypage/delivery")
    public String loadMyPageDelivery(Model model){

        List<MemberVo> products = new ArrayList<>();
        products.add(new MemberVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new MemberVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_delivery";
    }

    @GetMapping("/{memberId}/mypage/cart")
    public String loadCart(Model model){

        List<MemberVo> products = new ArrayList<>();
        products.add(new MemberVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new MemberVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new MemberVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_cart";
    }

    @GetMapping("/{memberId}/delivery_reservation")
    public String loadDeliveryReservationPage(Model model){
        return "pages/member/delivery_reservation";
    }


}