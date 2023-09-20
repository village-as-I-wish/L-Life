package kosa.com.suntofu.L_LIFE;

import kosa.com.suntofu.L_LIFE.member.SessionConst;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/main2")
    public String loadLlifeNewMainPage(Model model){
        return "pages/main/new_main";
    }

    @GetMapping("/mail")
    public String loadMail(Model model){
        return "pages/notification/mail_alert";
    }


    @GetMapping("/book")
    public String loadBook(Model model){
        return "pages/main/book";
    }
}
