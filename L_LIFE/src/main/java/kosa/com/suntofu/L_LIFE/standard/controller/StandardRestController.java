package kosa.com.suntofu.L_LIFE.standard.controller;

import kosa.com.suntofu.L_LIFE.standard.service.StandardService;
import kosa.com.suntofu.L_LIFE.standard.vo.SearchRequestVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardOptionVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/standard")
@RequiredArgsConstructor
public class StandardRestController {

    private final StandardService standardService;

    // 스탠다드 상품(브랜드, 분위기, 코인개수) 검색
    @GetMapping("/search")
    public ResponseEntity<List<StandardVo>> search(SearchRequestVo requestVo) {
        List<StandardVo> FilterProducts = standardService.getStandardProductByFilter(requestVo);
        return new ResponseEntity<>(FilterProducts, HttpStatus.OK);
    }

    // 재고 확인
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
        StandardOptionVo option = new StandardOptionVo(lfOptId, 0, lfId, "", memberId);
        int result = standardService.putOptionToReservation(option);
        log.info("result{}", result);
        return result;
    }
}