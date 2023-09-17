package kosa.com.suntofu.L_LIFE.community.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @GetMapping("/main")
    public String loadCommunityMainPage(Model model){
        return "pages/community/community_main";
    }

    @GetMapping("/report")
    public String loadReportPage(Model model){
        return "pages/community/community_report";
    }


    @GetMapping("/3d")
    public String load3dPage(Model model){
        return "pages/community/community_3d";
    }


}