package kosa.com.suntofu.L_LIFE.standard.controller;

import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/standard")
public class StandardController {

    private final StandardService standardService;
    @GetMapping("/main")
    public String loadStandardMainPage(Model model){
//        model.addAttribute("data", "test");
        return "pages/standard/main";
    }

    @GetMapping(value="all")
    public String showAllStandardFurniture(Model model){
        //List<StandardVO> standardList = standardService.getAllStandard();
      //  System.out.println(standardList);
        return "pages/subscription/subscription_select";
    }

    @GetMapping(value="standard_payment_detail")
    public String standardPaymentDetail(Model model){
        //List<StandardVO> standardList = standardService.getAllStandard();
        //  System.out.println(standardList);
        return "pages/subscription/standard_payment_detail";
    }
}
