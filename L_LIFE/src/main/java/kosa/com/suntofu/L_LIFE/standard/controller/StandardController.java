package kosa.com.suntofu.L_LIFE.standard.controller;

import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        return "pages/standard/livart_main";
    }

    @GetMapping(value = "subscription_guide")
    public String subscriptionGuide(Model model) {
        return "pages/subscription/subscription_guide";
    }
}
