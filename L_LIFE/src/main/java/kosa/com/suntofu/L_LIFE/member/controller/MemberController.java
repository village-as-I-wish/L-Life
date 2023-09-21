package kosa.com.suntofu.L_LIFE.member.controller;

import kosa.com.suntofu.L_LIFE.member.SessionConst;
import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.*;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @Value("${delivery-key}")
    private String deliveryKey;

    @Value("${baseurl}")
    private String baseUrl;

    @GetMapping("/login")
    public String loadLoginPage(){ return "pages/member/login"; }

    @PostMapping("/login")
    public String kakaoLogin(@RequestParam String email, @RequestParam String name, @RequestParam String gender, @RequestParam String profile, HttpServletRequest request, Model model){
        MemberVo memberVo = new MemberVo(1,name,gender,0,0,"",email,profile,"",0);
        MemberVo existingMember = memberService.insertOrSelectMember(memberVo);

        Integer currentCoin = memberService.getCurrentCoin(existingMember.getMId());
        int orderCount = memberService.getOrderCount(existingMember.getMId());
        int deliveryReady = memberService.getDeliveryReadyStatus(existingMember.getMId());
        int deliveryProgress = memberService.getDeliveryProgressStatus(existingMember.getMId());
        int deliveryComplete = memberService.getDeliveryCompleteStatus(existingMember.getMId());


        HttpSession session = request.getSession();
        // 세션 저장
        session.setAttribute("existingMember", existingMember);
        session.setAttribute("currentCoin", currentCoin);
        session.setAttribute("orderCount",orderCount);
        session.setAttribute("deliveryReady",deliveryReady);
        session.setAttribute("deliveryProgress",deliveryProgress);
        session.setAttribute("deliveryComplete",deliveryComplete);

        return "pages/main/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션을 무효화하여 로그아웃 처리
        return "pages/main/main"; // 로그아웃 후 홈페이지로 리다이렉트
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
        SubscriptionVo standardSubscription = memberService.getStandardSubscription(memberId);

        model.addAttribute("standardSubscription", standardSubscription);
        model.addAttribute("standardSubscriptionList", standardSubscriptionList);
        model.addAttribute("baseUrl",baseUrl);

        return "pages/member/mypage_standard";
    }

    @GetMapping("/{memberId}/mypage/premium")
    public String loadMyPagePremium(Model model, @PathVariable int memberId){

        List<SubscriptionListVo> premiumSubscriptionList = memberService.getAllpremiumScriptionList(memberId);
        model.addAttribute("premiumSubscriptionList",premiumSubscriptionList);
        model.addAttribute("baseUrl",baseUrl);
        System.out.println("프리미엄" + premiumSubscriptionList);
        return "pages/member/mypage_premium";
    }

    @GetMapping("/{memberId}/mypage/delivery")
    public String loadMyPageDelivery(Model model, @PathVariable int memberId){

        List<DeliveryListVo> deliveryList = memberService.getDeliveryList(memberId);
        model.addAttribute("deliveryList",deliveryList);
        model.addAttribute("deliveryKey",deliveryKey);
        return "pages/member/mypage_delivery";
    }

    @GetMapping("/{memberId}/mypage/cart")
    public String loadCart(Model model, @PathVariable int memberId){
        // 스탠다드 장바구니
        List<CartVo> standardCarts  = memberService.getAllStandardCarts(memberId);
        // 프리미엄 장바구니
        List<CartVo> premiumCarts  = memberService.getAllPremiumCarts(memberId);


        SubscriptionVo standardSubscription = memberService.getStandardSubscription(memberId);
        SubscriptionVo premiumSubscription = memberService.getPremiumSubscription(memberId);

        log.info("premium carts : {}",premiumCarts);
        log.info("standard carts : {}",standardCarts);

        model.addAttribute("baseUrl",baseUrl);
        model.addAttribute("standardCarts", standardCarts);
        model.addAttribute("premiumCarts", premiumCarts);
        model.addAttribute("standardSubscription", standardSubscription);
        model.addAttribute("premiumSubscription", premiumSubscription);

        return "pages/member/mypage_cart";
    }

    @GetMapping("/{memberId}/delivery_reservation")
    public String loadDeliveryReservationPage(Model model){
        return "pages/member/delivery_reservation";
    }


}