package kosa.com.suntofu.L_LIFE.standard.controller;

import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardLiveVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/standard")
public class StandardController {

    private final StandardService standardService;
    private int lfSubType = 0;

    @GetMapping("/main")
    public String loadStandardMainPage(Model model) {

        model.addAttribute("lfSubType", lfSubType);

        // 메인 상품 리스트 가져오기
        List<StandardVo> standardList = standardService.getAllStandard();
        model.addAttribute("standardList", standardList);
        System.out.println(standardList);

        // 라이브 리스트 가져오기
        List<StandardLiveVo> standardLiveList = standardService.getAllLiveStream();
        model.addAttribute("standardLiveList", standardLiveList);
        System.out.println(standardLiveList);

        // 현재 날짜와 시간 가져오기
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", LocalDateTime.now());

        // 상품 개수 표현
        model.addAttribute("productCount", standardList.size());
        return "pages/standard/standard_main";
    }

    // 스탠다드 상품 카테고리별 필터링
    @GetMapping("/category/{fCategoryId}")
    public String getStandardByCategory(Model model, @PathVariable int fCategoryId) {

        model.addAttribute("lfSubType", lfSubType);

        List<StandardVo> standardList = standardService.getStandardByCategory(fCategoryId);
        model.addAttribute("standardList", standardList);

        List<StandardLiveVo> standardLiveList = standardService.getAllLiveStream();
        model.addAttribute("standardLiveList", standardLiveList);

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", LocalDateTime.now());

        model.addAttribute("productCount", standardList.size());
        return "pages/standard/standard_main";
    }


    // 스탠다드 상품 검색
    @GetMapping("/search")
    public String getStandardProductByKeyword(@RequestParam String keyword, Model model) {

        model.addAttribute("lfSubType", lfSubType);

        List<StandardLiveVo> standardLiveList = standardService.getAllLiveStream();
        model.addAttribute("standardLiveList", standardLiveList);

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", LocalDateTime.now());

        List<StandardVo> stkeyword = standardService.getStandardProductByKeyword(keyword);
        model.addAttribute("standardList", stkeyword);

        model.addAttribute("productCount", stkeyword.size());
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
