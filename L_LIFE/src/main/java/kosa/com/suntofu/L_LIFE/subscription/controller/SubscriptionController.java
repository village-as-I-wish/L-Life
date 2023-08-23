package kosa.com.suntofu.L_LIFE.subscription.controller;

import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/")
    public String loadGuidePage(){
        return "pages/subscription/subscription_guide";
    }

}
