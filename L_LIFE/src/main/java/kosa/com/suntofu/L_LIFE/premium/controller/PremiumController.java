package kosa.com.suntofu.L_LIFE.premium.controller;

import kosa.com.suntofu.L_LIFE.premium.service.PremiumService;
import kosa.com.suntofu.L_LIFE.premium.vo.PackageDetailVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumDetailVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/premium")
public class PremiumController {

    private final PremiumService premiumService;

    @GetMapping()
    public String loadPremiumPackageMainPage(Model model){

        model.addAttribute("MDPickPackages", premiumService.getMDPickPackages());
        model.addAttribute("PromotionPackages", premiumService.getPromotionPackages());
        return "pages/premium/premium_package_main";
    }

    @GetMapping("/package/{lfPackageId}/detail")
    public String loadPremiumPackageDetail(@PathVariable int lfPackageId, Model model){
        PackageDetailVo packageDetail = premiumService.getPremiumPackageDetail(lfPackageId);
        log.info("packageDetail {}", packageDetail);

        model.addAttribute("packageDetail", packageDetail);
        return "pages/premium/premium_package_detail";
    }
//-------------------------------------------------------------------------------------------------------

    @GetMapping("/main")
    public String loadPremiumMainPage(PaginationVo paginationVo, Model model){
        List<PremiumVo> premiumProductList = premiumService.selectPremiumProductList(paginationVo);
        int totalNum = premiumService.selectProductCountPagination(paginationVo);
        int paginationNum = premiumService.calculatePaginationNum(totalNum);

        model.addAttribute("premiumProducts", premiumProductList);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("paginationNum", paginationNum);
        model.addAttribute("page", paginationVo.getPage());
        model.addAttribute("lfSubType", paginationVo.getLfSubType());

        return "pages/premium/premium_main";
    }

    @GetMapping("/category/{lfCategoryId}")
    public String loadPremiumMainPageByCategory(@PathVariable int lfCategoryId, PaginationVo paginationVo, Model model){
        paginationVo.setLfCategoryId(lfCategoryId);
        List<PremiumVo> premiumProductListByCategory = premiumService.selectProductByCategory(paginationVo);
        int totalNum = premiumService.selectProductCountByCategoryByPagination(paginationVo);
        int paginationNum = premiumService.calculatePaginationNum(totalNum);

        model.addAttribute("premiumProducts", premiumProductListByCategory);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("paginationNum", paginationNum);
        model.addAttribute("page", paginationVo.getPage());
        model.addAttribute("lfSubType", paginationVo.getLfSubType());

        return "pages/premium/premium_main";
    }

    @GetMapping("/search")
    public String loadPremiumMainPageByKeyword(@RequestParam String keyword, PaginationVo paginationVo, Model model){
        paginationVo.setKeyword(keyword);
        List<PremiumVo> premiumProductListByKeyword = premiumService.selectProductByKeyword(paginationVo);
        int totalNum = premiumService.selectProductByKeywordPagination(paginationVo);
        int paginationNum = premiumService.calculatePaginationNum(totalNum);

        model.addAttribute("premiumProducts", premiumProductListByKeyword);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("paginationNum", paginationNum);
        model.addAttribute("page", paginationVo.getPage());

        return "pages/premium/premium_main";
    }

    @GetMapping("/{productId}/detail")
    public String loadPremiumDetailPage(@PathVariable("productId") int lfId, Model model){
        PremiumVo premiumDetailById = premiumService.selectPremiumProductDetailById(lfId);
        model.addAttribute("premiumDetail", premiumDetailById);

        return "pages/premium/premium_detail";
    }
}