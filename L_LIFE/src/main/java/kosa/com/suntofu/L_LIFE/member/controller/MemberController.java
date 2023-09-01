package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import kosa.com.suntofu.L_LIFE.member.vo.CartVo;
import kosa.com.suntofu.L_LIFE.member.vo.SubscriptionListVo;
import kosa.com.suntofu.L_LIFE.member.vo.TestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loadLoginPage(){ return "pages/member/login"; }

    @PostMapping("/login")
    public String kakaoLogin(@RequestParam String email, @RequestParam String name, @RequestParam String gender, @RequestParam String profile, HttpSession session){
        MemberVo memberVo = new MemberVo(1,name,gender,0,0,"",email,profile,"");
        MemberVo existingMember = memberService.insertOrSelectMember(memberVo);
        int currentCoin = memberService.getCurrentCoin(existingMember.getMId());
        // 세션 저장
        session.setAttribute("loggedInMemberInfo", existingMember);
        session.setAttribute("currentCoin", currentCoin);
        return "pages/main/main";
    }

    @GetMapping("/checkSession")
    @ResponseBody
    public String checkSession(HttpSession session) {
        MemberVo loggedInMember = (MemberVo) session.getAttribute("loggedInMemberInfo");

        if (loggedInMember != null) {
            // 세션에 저장된 정보 출력 또는 활용
            return "Logged in member's name: " + loggedInMember.getMName();
        } else {
            return "No user logged in.";
        }
    }
  
    @GetMapping("/{memberId}/mypage")
    public String loadMyPage(Model model){

        List<TestVo> products = new ArrayList<>();
        products.add(new TestVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new TestVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new TestVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new TestVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new TestVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_main";
    }

    @GetMapping("/{memberId}/mypage/standard")
    public String loadMyPageStandard(Model model, @PathVariable int memberId){
        // 스탠다드 구독내역
        List<SubscriptionListVo> standardSubscriptionList  = memberService.getAllStandardScriptionList(memberId);

        System.out.println("standardSubscriptionList" + standardSubscriptionList);

        model.addAttribute("standardSubscriptionList", standardSubscriptionList);

        return "pages/member/mypage_standard";
    }

    @GetMapping("/{memberId}/mypage/premium")
    public String loadMyPagePremium(Model model){

        List<TestVo> products = new ArrayList<>();
        products.add(new TestVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new TestVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new TestVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new TestVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new TestVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_premium";
    }

    @GetMapping("/{memberId}/mypage/delivery")
    public String loadMyPageDelivery(Model model){

        List<TestVo> products = new ArrayList<>();
        products.add(new TestVo("2023-08-20", "product1_sample.jpeg", "제품1", 3, "월33,000", 28000,19970526, 12341234, 24));
        products.add(new TestVo("2023-08-20", "product2_sample.jpeg", "제품2", 5, "월33,000", 54000,19970526, 12341234, 12));
        products.add(new TestVo("2023-08-20", "product3_sample.jpeg", "제품3", 1, "월33,000", 15000,19970526, 12341234, 6));
        products.add(new TestVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        products.add(new TestVo("2023-08-20", "product4_sample.jpeg", "제품4", 4, "월33,000", 48000,19970526, 12341234, 24));
        model.addAttribute("products", products);
        return "pages/member/mypage_delivery";
    }

    @GetMapping("/{memberId}/mypage/cart")
    public String loadCart(Model model, @PathVariable int memberId){
        // 스탠다드 장바구니
        List<CartVo> standardCarts  = memberService.getAllStandardCarts(memberId);

        // 프리미엄 장바구니
        List<CartVo> premiumCarts  = memberService.getAllPremiumCarts(memberId);

        System.out.println("standard cart" + standardCarts);
        System.out.println("premium cart" + premiumCarts);

        model.addAttribute("standardCarts", standardCarts);
        model.addAttribute("premiumCarts", premiumCarts);
        return "pages/member/mypage_cart";
    }

    @GetMapping("/{memberId}/delivery_reservation")
    public String loadDeliveryReservationPage(Model model){
        return "pages/member/delivery_reservation";
    }


}