package kosa.com.suntofu.L_LIFE;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String loadLivartMainPage(){
        return "pages/main/livart_main";
    }
}
