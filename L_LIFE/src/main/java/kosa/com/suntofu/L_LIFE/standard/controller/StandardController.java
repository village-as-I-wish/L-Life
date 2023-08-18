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
    @GetMapping
    public String loadStandardMainPage(Model model){
        model.addAttribute("data", "test");
        return "livart_main";
    }
}
