package kosa.com.suntofu.L_LIFE.subscription.controller;

import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import kosa.com.suntofu.L_LIFE.subscription.vo.BillVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayFurnitureVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayKeysVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


    @GetMapping(value="/standard/delivery_reservation")
    public String loadStandardDeliveryReservation(Model model,
                                            HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("stPaymentProduct"));
        model.addAttribute("stPaymentProduct",session.getAttribute("stPaymentProduct"));
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("payKeys", payKeysVO);
        return "pages/member/delivery_reservation";
    }

    @PostMapping(value="/standard/payment_detail")
    @ResponseBody
    public Map<String, String> postStandardDeliveryReservation(@RequestBody List<PayFurnitureVo> selectedStProducts,
                                                         HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("stPaymentProduct",selectedStProducts);
        log.info("st pay product : {}",selectedStProducts);
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "http://localhost:8080/l-life/subscription/standard/delivery_reservation");

        return response;
    }

    /**
     * 프리미엄 구독 이용료 결제 페이지 로드
     * @param
     * @return String
     */
    @PostMapping(value="/premium/payment_detail")
    @ResponseBody
    public Map<String, String> loadPremiumPaymentDetail(@RequestBody List<PayFurnitureVo> selectedProducts,
                                                        HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("prPaymentProduct",selectedProducts);
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "http://localhost:8080/l-life/subscription/premium/payment_detail");

        return response;
    }


    @GetMapping(value="/premium/payment_detail")
    public String postPremiumPaymentDetail(Model model,
                                           HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info("postPremiumPaymentDetail : {}",session.getAttribute("prPaymentProduct"));
        model.addAttribute("paymentProduct",session.getAttribute("prPaymentProduct"));
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("payKeys", payKeysVO);
        return "pages/subscription/premium_payment_detail";
    }

}
