package kosa.com.suntofu.L_LIFE.standard.controller;

import kosa.com.suntofu.L_LIFE.common.vo.ReviewVo;
import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/standard")
public class StandardController {
    @Value("${baseurl}")
    private String baseUrl;
    private final StandardService standardService;
    private int lfSubType = 0;

    @GetMapping("/main")
    public String loadStandardMainPage(Model model, StandardPaginationVo standardPaginationVo) {

        int totalNum = standardService.getAllStandardPagination(standardPaginationVo);
        int paginationNum = standardService.calculatePaginationNum(totalNum);

        // 메인 상품 리스트 가져오기
        List<StandardVo> standardList = standardService.getAllStandard(standardPaginationVo);
        model.addAttribute("standardList", standardList);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("paginationNum", paginationNum);
        model.addAttribute("page", standardPaginationVo.getPage());

        // 라이브 리스트 가져오기
        List<StandardLiveVo> standardLiveList = standardService.getAllLiveStream();
        model.addAttribute("standardLiveList", standardLiveList);

        // 현재 날짜와 시간 가져오기
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", LocalDateTime.now());

        // 상품 개수 표현
        model.addAttribute("productCount", standardList.size());
        return "pages/standard/standard_main";
    }

    // 스탠다드 상품 카테고리별 필터링
    @GetMapping("/category/{lfCategoryId}")
    public String selectStandardProductByCategory(Model model, @PathVariable int lfCategoryId,  StandardPaginationVo standardPaginationVo) {

        standardPaginationVo.setLfCategoryId(lfCategoryId);
        List<StandardVo> standardList = standardService.getStandardProductByCategory(standardPaginationVo);
        int totalNum = standardService.getStandardProductByCategoryByPagination(standardPaginationVo);
        int paginationNum = standardService.calculatePaginationNum(totalNum);

        model.addAttribute("standardList", standardList);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("paginationNum", paginationNum);
        model.addAttribute("page", standardPaginationVo.getPage());
        model.addAttribute("lfSubType", standardPaginationVo.getLfSubType());

        List<StandardLiveVo> standardLiveList = standardService.getAllLiveStream();
        model.addAttribute("standardLiveList", standardLiveList);

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", LocalDateTime.now());

        model.addAttribute("productCount", standardList.size());
        return "pages/standard/standard_main";
    }


    // 스탠다드 상품 검색
    @GetMapping("/search")
    public String selectStandardProductByKeyword(@RequestParam String keyword, Model model, StandardPaginationVo standardPaginationVo) {

        standardPaginationVo.setKeyword(keyword);
        List<StandardVo> stProductListByKeyword = standardService.getStandardProductByKeyword(standardPaginationVo);
        int totalNum = standardService.getStandardProductByKeywordByPagination(standardPaginationVo);
        int paginationNum = standardService.calculatePaginationNum(totalNum);
        model.addAttribute("paginationNum", paginationNum);
        model.addAttribute("stkeyword", stProductListByKeyword);
        model.addAttribute("standardList", stProductListByKeyword);
        model.addAttribute("productCount", stProductListByKeyword.size());
        model.addAttribute("totalNum", totalNum);

        List<StandardLiveVo> standardLiveList = standardService.getAllLiveStream();
        model.addAttribute("standardLiveList", standardLiveList);

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", LocalDateTime.now());

      return "pages/standard/standard_main";
    }


    /**
     * 스탠다드 구독관 제품 상세 페이지 로드
     *
     * @param model
     * @return String
     */
    @GetMapping("/{productId}/detail")
    public String getStandardDetailPage(@PathVariable("productId") int lfId, Model model) {

        model.addAttribute("baseUrl", baseUrl);
        // 기본적인 상세정보 가져오기 + 리퍼 사진 정보
        StandardDetailVo detail = standardService.getStandardDetailById(lfId);
        model.addAttribute("standardDetail",  detail);
        log.info("Standard Detail  {}  : " , detail );
        // 옵션 가져오기
        List<StandardOptionVo> options = standardService.getStandardOptionById(lfId);
        model.addAttribute("options", options);

        /*코드 리팩터링 -> 기본 정보 가져올 때로 합침 */
//        List<StandardRefurVo> refurinfos = standardService.getStandardRefurById(lfId);
//        model.addAttribute("refurInfos", refurinfos);
//        model.addAttribute("lfId", lfId);

        // 카테고리 BEST
        List<StandardVo> recommendProducts = standardService.getStandardRecommendation(detail);
        model.addAttribute("recommendProducts", recommendProducts);

        // Review 정보 가져오기
        List<ReviewVo> reviewList = standardService.getReviews(lfId);
        log.info("reviewList {}", reviewList);
        model.addAttribute("reviewList", reviewList);
        return "pages/standard/standard_detail";
    }


    @GetMapping("/review")
    public String loadReviewCreatePage() {
        return "pages/standard/review";
    }
}
