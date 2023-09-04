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
    @ResponseBody
    public int loadStockAmount(@PathVariable("optionId") int lfOptId, @PathVariable("productId") int lfId) {
        return premiumService.selectPremiumStockAmount(lfOptId, lfId);
    }

    @PostMapping("/reservation")
    @ResponseBody
    public int insertOptionToReservation(@RequestParam int lfId,
                                      @RequestParam int lfOptId,
                                         @RequestParam int memberId) {
        PremiumOptionVo option = new PremiumOptionVo(lfId, 0, 0, lfOptId, "", memberId);
        int result = premiumService.insertOptionToReservation(option);

        return result;
    }
}
