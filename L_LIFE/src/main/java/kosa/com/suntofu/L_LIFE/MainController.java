package kosa.com.suntofu.L_LIFE;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String loadLivartMainPage(Model model){
        return "pages/main/livart_main";
    }

    @GetMapping("/main")
    public String loadLlifeMainPage(Model model){
        return "pages/main/main";
    }

    @GetMapping("/mail")
    public String loadMail(Model model){
        return "pages/notification/mail_alert";
    }
}
