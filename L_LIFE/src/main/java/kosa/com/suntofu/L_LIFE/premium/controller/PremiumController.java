package kosa.com.suntofu.L_LIFE.premium.controller;

import kosa.com.suntofu.L_LIFE.premium.service.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/premium")
public class PremiumController {

    private final PremiumService premiumService;
    
    @GetMapping()
    public String loadPremiumMainPage(){
        return "pages/premium/premium_main";
    }

    @GetMapping("/package/{packageNum}/detail")
    public String loadPremiumPackageDetail(){
        return "pages/premium/premium_package_detail";
    }
}
