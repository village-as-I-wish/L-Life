package kosa.com.suntofu.L_LIFE.subscription.controller;

import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

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
    @GetMapping(value="/standard/{standardType}/standard_payment_detail")

    public String loadStandardPaymentDetail(Model model){
        //List<StandardVO> standardList = standardService.getAllStandard();
        //  System.out.println(standardList);
        return "pages/subscription/standard_payment_detail";
    }
}
