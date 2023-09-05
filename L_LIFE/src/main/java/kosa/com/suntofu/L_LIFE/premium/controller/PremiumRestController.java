package kosa.com.suntofu.L_LIFE.premium.controller;

import kosa.com.suntofu.L_LIFE.premium.service.PremiumService;
import kosa.com.suntofu.L_LIFE.premium.vo.PaginationVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumOptionVo;
import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/premium")
@RequiredArgsConstructor
public class PremiumRestController {
    private final PremiumService premiumService;

    @GetMapping("/search")
    public ResponseEntity<List<PremiumVo>> search(PaginationVo paginationVo) {

        List<PremiumVo> FilterProducts = premiumService.selectProductByFilter(paginationVo);
        return new ResponseEntity<>(FilterProducts, HttpStatus.OK);
    }

    @GetMapping("/checkStock/{productId}/{optionId}")
    public ResponseEntity<Integer> loadStockAmount(@PathVariable("optionId") int lfOptId, @PathVariable("productId") int lfId) {
        int stockAmount = premiumService.selectPremiumStockAmount(lfOptId, lfId);
        return ResponseEntity.ok(stockAmount);
    }

    @PostMapping("/reservation")
    public ResponseEntity<Integer> insertOptionToReservation(@RequestParam int lfId,
                                                             @RequestParam int lfOptId,
                                                             @RequestParam int memberId) {
        PremiumOptionVo option = new PremiumOptionVo(lfId, 0, 0, lfOptId, "", memberId);
        int result = premiumService.insertOptionToReservation(option);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/cart")
    public ResponseEntity<Integer> insertPremiumProductToCart(@RequestParam int lfId,
                                                              @RequestParam int lfOptId,
                                                              @RequestParam int memberId) {
        PremiumOptionVo cart = new PremiumOptionVo(lfId, 0, 0, lfOptId, "", memberId);
        int result = premiumService.insertPremiumProductToCart(cart);
        log.info("result: {}", result);
        log.info("cart: {}", cart);
        return ResponseEntity.ok(result);
    }
}
