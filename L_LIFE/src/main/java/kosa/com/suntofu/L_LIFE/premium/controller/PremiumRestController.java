package kosa.com.suntofu.L_LIFE.premium.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewRequestVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewVo;
import kosa.com.suntofu.L_LIFE.premium.service.PremiumService;
import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumOptionVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import kosa.com.suntofu.L_LIFE.common.CartReturn;
import kosa.com.suntofu.L_LIFE.common.vo.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/premium")
@RequiredArgsConstructor
@Tag(name = "premium", description = "프리미엄 API")
public class PremiumRestController {

    @Value("${baseurl}")
    private String baseUrl;
    private final PremiumService premiumService;

    @Operation(summary = "프리미엄 상품 검색", description = "프리미엄 필터를 통해 상품을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<PremiumVo>> search(PaginationVo paginationVo, Model model) {

        List<PremiumVo> FilterProducts = premiumService.selectProductByFilter(paginationVo);
        model.addAttribute("baseUrl", baseUrl);
        return new ResponseEntity<>(FilterProducts, HttpStatus.OK);
    }

    @Operation(summary = "프리미엄 재고 확인", description = "프리미엄상품 - 옵션 재고를 확인합니다.")
    @GetMapping("/checkStock/{productId}/{optionId}")
    public ResponseEntity<Integer> loadStockAmount(@PathVariable("optionId") int lfOptId, @PathVariable("productId") int lfId) {
        int stockAmount = premiumService.selectPremiumStockAmount(lfOptId, lfId);
        return ResponseEntity.ok(stockAmount);
    }

    @Operation(summary = "프리미엄 상품 재입고 알림신청", description = "프리미엄상품 재입고 알림신청합니다.")
    @PostMapping("/reservation")
    public ResponseEntity<Integer> insertOptionToReservation(@RequestParam int lfId,
                                                             @RequestParam int lfOptId, @RequestParam int memberId) {
        PremiumOptionVo option = new PremiumOptionVo(lfId, 0, 0, lfOptId, "", memberId);
        int result = premiumService.insertOptionToReservation(option);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "프리미엄 상품 장바구니 담기", description = "프리미엄상품을 장바구니에 담습니다.")
    @PostMapping("/cart")
    public ResponseEntity<BasicResponse> insertPremiumProductToCart(CartItemVO cartItemVo) {
        log.info("Premium Product To Cart {} ", cartItemVo);
        int result = premiumService.insertPremiumProductToCart(cartItemVo);
        if(result == CartReturn.CART_ADD_ERROR){ // result 1
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("상품 담기 실패").result(CartReturn.CART_ADD_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result == CartReturn.NO_SUBSCRIPTION_EXIST){ // result 0
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("구독권이 없어 장바구니에 담지 못함").result(CartReturn.NO_SUBSCRIPTION_EXIST).build(), HttpStatus.OK);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("상품 장바구니 담기 성공").result(CartReturn.CART_ADD_SUCCESS).build(), HttpStatus.OK);

    }

    @Operation(summary = "스탠다드 상품 리뷰 작성 ", description = "스탠상품 리뷰를 작성합니다.")
    @PostMapping("/review")
    @ResponseBody
    public ResponseEntity<BasicResponse> createReview(ReviewRequestVo reviewRequestVo){
        log.info("[프리미엄 리뷰 등록 ] 요청 VO {} ", reviewRequestVo);
        try{
            ReviewVo result = premiumService.createReview(reviewRequestVo);
            log.info("[프리미엄 리뷰 등록 완료 ] 결과 vo {} ", result);
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("리뷰 작성완료").result(result).build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("리뷰 등록 실패").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "스탠다드 상품 리뷰 삭제 ", description = "스탠상품 리뷰를 삭제합니다.")
    @DeleteMapping("/review")
    @ResponseBody
    public ResponseEntity<BasicResponse> deleteReview(int lfReviewId){

        log.info("[프리미엄 리뷰 삭제 ] 요청 ReviewId{} ", lfReviewId);
        int result = premiumService.deleteReview(lfReviewId);
        log.info("[프리미엄 리뷰 삭제 ] 결과 {} ", result);
        if(result == 1){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("리뷰 식제 완료").build(), HttpStatus.OK);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("리뷰 등록 실패").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
