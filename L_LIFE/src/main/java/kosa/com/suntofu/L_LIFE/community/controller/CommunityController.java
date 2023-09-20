package kosa.com.suntofu.L_LIFE.community.controller;

import kosa.com.suntofu.L_LIFE.community.service.CommunityService;
import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @Value("${gpt-token}")
    private String gptToken;

    @GetMapping("/main")
    public String loadCommunityMainPage( Model model){
        List<BookVo> books = communityService.selectBooks();
        model.addAttribute("books", books);
        log.info("books {}  : " , books );

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


    @GetMapping("/write")
    public String loadCommunityWritePage(Model model){
        model.addAttribute("gptToken", gptToken);
        return "pages/community/community_write";
    }

    @GetMapping("/ex")
    public String Ex(Model model){
        return "pages/community/book_prac";
    }

}
