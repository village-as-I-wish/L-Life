package kosa.com.suntofu.L_LIFE.subscription.controller;

import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayKeysVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    @Value("${baseurl}")
    private String baseUrl;

    private final PayKeysVo payKeysVO;

    private final SubscriptionService subscriptionService;

    /**
     * 이용 구독 서비스 페이지 로드
     * @return String
     */
    @GetMapping()
    public String loadGuidePage(){
        return "pages/subscription/subscription_guide";
    }

    /**
     * 스탠다드 구독 종류 선택 페이지 로드
     * @param model
     * @return
     */
    @GetMapping("/standard/standard_selection")
    public String loadStandardSelectionPage(Model model){

        return "pages/subscription/subscription_selection";
    }

    /**
     * 스탠다드 구독 이용료 결제 페이지 로드
     * @param model
     * @return String
     */
    @GetMapping(value="/standard/payment_detail")
    public String loadStandardPaymentDetail(Model model, @RequestParam int subscriptionPlanId){
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("payKeys", payKeysVO);
        log.info("SubscriptionType {}", subscriptionPlanId);
        BillVo billVo = subscriptionService.getSubscriptionPayBill(subscriptionPlanId);
        model.addAttribute("billVo", billVo);
        return "pages/subscription/standard_payment_detail";
    }

    /**
     * 프리미엄 구독 이용료 결제 페이지 로드
     * @param model
     * @return String
     */
    @GetMapping(value="/premium/payment_detail")

    public String loadPremiumPaymentDetail(Model model){
        //List<StandardVO> standardList = standardService.getAllStandard();
        //  System.out.println(standardList);
        model.addAttribute("payKeys", payKeysVO);
        return "pages/subscription/premium_payment_detail";
    }


//    public String loadPaymentSuccessPage(){
//
//    }
}
