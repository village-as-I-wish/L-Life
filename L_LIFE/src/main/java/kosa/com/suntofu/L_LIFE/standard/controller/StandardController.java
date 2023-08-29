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

    /*
        스탠다드 구독관 메인 ( 리스트 ) 페이지
     */
    private final StandardService standardService;

    /**
     * 스탠다드 구독관 메인 ( 리스트 ) 페이지 로드
     * @param model
     * @return String
     */
    @GetMapping("")
    public String loadStandardMainPage(Model model){
        return "pages/standard/standard_main";
    }

    /**
     * 스탠다드 구독관 제품 상세 페이지 로드
     * @param model
     * @return String
     */
    @GetMapping("/{productId}/detail")
    public String loadStandardDetailPage(Model model){
        return "pages/standard/standard_detail";
    }


    @GetMapping("/review")
    public String loadReviewCreatePage(){
        return "pages/standard/review";
    }
}
