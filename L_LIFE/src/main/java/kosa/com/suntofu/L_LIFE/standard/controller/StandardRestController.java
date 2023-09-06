package kosa.com.suntofu.L_LIFE.standard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import kosa.com.suntofu.L_LIFE.subscription.vo.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/standard")
@RequiredArgsConstructor
@Tag(name = "standard", description = "스탠다드 API")
public class StandardRestController {

    private final StandardService standardService;

    @Operation(summary = "스탠다드 상품 검색", description = "스탠다드 필터를 통해 상품을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<StandardVo>> search(SearchRequestVo requestVo) {
        List<StandardVo> FilterProducts = standardService.getStandardProductByFilter(requestVo);
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
    @PostMapping("/insertcart")
    @ResponseBody
    public ResponseEntity<String> putProductToCart(@RequestParam int lfOptId,
                                @RequestParam int lfId,
                                @RequestParam int memberId) {

        StandardSubscriptionVo tocart = new StandardSubscriptionVo(lfId, memberId, lfOptId);
        int result = standardService.putProductToCart(tocart);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Operation(summary = "스탠다드 상품 리뷰 작성 ", description = "스탠상품 리뷰를 작성합니다.")
    @PostMapping("/review")
    @ResponseBody
    public ResponseEntity<BasicResponse> createReview(ReviewRequestVo reviewRequestVo){

        log.info("[리뷰 등록 ] 요청 VO {} ", reviewRequestVo);
        int result  = standardService.createReview(reviewRequestVo);
        if(result < 1){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("리뷰 등록 실패").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("리뷰 작성완료").build(), HttpStatus.OK);
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