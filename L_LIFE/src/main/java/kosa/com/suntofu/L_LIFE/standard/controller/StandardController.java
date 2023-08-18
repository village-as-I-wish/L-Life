package kosa.com.suntofu.L_LIFE.standard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/standard")
public class StandardController {

    @GetMapping
    public String loadStandardMainPage(Model model){
        model.addAttribute("data", "test");
        return "livart_main";
    }
}
