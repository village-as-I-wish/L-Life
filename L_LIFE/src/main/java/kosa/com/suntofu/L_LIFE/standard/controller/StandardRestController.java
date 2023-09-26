package kosa.com.suntofu.L_LIFE.standard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import kosa.com.suntofu.L_LIFE.common.util.CartReturn;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
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
@RequestMapping("/api/v1/standard")
@RequiredArgsConstructor
@Tag(name = "standard", description = "스탠다드 API")
public class StandardRestController {

    @Value("${baseurl}")
    private String baseUrl;
    private final StandardService standardService;

    @Operation(summary = "스탠다드 상품 검색", description = "스탠다드 필터를 통해 상품을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<StandardVo>> search(SearchRequestVo requestVo, Model model) {
        List<StandardVo> FilterProducts = standardService.getStandardProductByFilter(requestVo);
        model.addAttribute("baseUrl", baseUrl);
        return new ResponseEntity<>(FilterProducts, HttpStatus.OK);
    }

    @Operation(summary = "스탠다드 재고 확인", description = "스탠상품 - 옵션 재고를 확인합니다.")
    @GetMapping("/checkStock/{productId}/{optionId}")
    @ResponseBody
    public int getStockAmount(@PathVariable("optionId") int lfOptId, @PathVariable("productId") int lfId) {
        return standardService.getStandardStockAmount(lfOptId, lfId);
    }

    // 상품 옵션 예약 테이블에 넣기
    @PostMapping("/reservation")
    @ResponseBody
    public int putOptionToReservation(@RequestParam int lfOptId,
                                      @RequestParam int lfId,
                                      @RequestParam int memberId) {
        StandardOptionVo option = new StandardOptionVo(lfId, 0, lfOptId, "", memberId);
        int result = standardService.putOptionToReservation(option);
        log.info("result{}", result);
        return result;
    }

    @Operation(summary = "스탠다드 상품 장바구니 담기", description = "스탠상품을 장바구니에 담습니다.")
    @PostMapping("/cart")
    @ResponseBody
    public ResponseEntity<BasicResponse> putProductToCart(CartItemVO cartItemVO) {
        log.info("Product To Cart {} ", cartItemVO);
        int result = standardService.putProductToCart(cartItemVO);
        if(result == CartReturn.CART_ADD_ERROR){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("상품 담기 실패").result(CartReturn.CART_ADD_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result == CartReturn.NO_SUBSCRIPTION_EXIST){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("구독권이 없어 장바구니에 담지 못함").result(CartReturn.NO_SUBSCRIPTION_EXIST).build(), HttpStatus.OK);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("상품 장바구니 담기 성공").result(CartReturn.CART_ADD_SUCCESS).build(), HttpStatus.OK);
    }

    @Operation(summary = "스탠다드 상품 한번에 장바구니 담기", description = "스탠상품 여러개를 장바구니에 담습니다.")
    @PostMapping("/carts")
    @ResponseBody
    public ResponseEntity<BasicResponse> putProductsToCart(@RequestBody  CartsRequestVo cartRequestVo) {
        int result = standardService.putProductsToCart(cartRequestVo);
        if(result != cartRequestVo.getCarts().size()){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("패키지 상품들 담기 실패").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("패키지 상품 장바구니 담기 성공").build(), HttpStatus.OK);
    }

    @Operation(summary = "스탠다드 상품 리뷰 작성 ", description = "스탠상품 리뷰를 작성합니다.")
    @PostMapping("/review")
    @ResponseBody
    public ResponseEntity<BasicResponse> createReview(ReviewRequestVo reviewRequestVo){
        log.info("[리뷰 등록 ] 요청 VO {} ", reviewRequestVo);
        try{
            ReviewVo result = standardService.createReview(reviewRequestVo);
            log.info("[리뷰 등록 완료 ] 결과 vo {} ", result);
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("리뷰 작성완료").result(result).build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("리뷰 등록 실패").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "스탠다드 상품 리뷰 삭제 ", description = "스탠상품 리뷰를 삭제합니다.")
    @DeleteMapping("/review")
    @ResponseBody
    public ResponseEntity<BasicResponse> deleteReview(int lfReviewId){

        log.info("[리뷰 삭제 ] 요청 ReviewId{} ", lfReviewId);
        int result = standardService.deleteReview(lfReviewId);
        log.info("[리뷰 삭제 ] 결과 {} ", result);
        if(result == 1){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("리뷰 식제 완료").build(), HttpStatus.OK);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("리뷰 등록 실패").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}