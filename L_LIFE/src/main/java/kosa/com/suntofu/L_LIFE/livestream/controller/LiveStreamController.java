package kosa.com.suntofu.L_LIFE.livestream.controller;

import kosa.com.suntofu.L_LIFE.livestream.service.LiveStreamService;
import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageProductVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LiveStreamDetailVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/livestream")
public class LiveStreamController {
    @Value("${baseurl}")
    private String baseUrl;
    private final LiveStreamService liveStreamService;

    /**
     *  라이브 상세 정보 ( 상품 포함 )
     * @return String
     */
    @GetMapping("/{lStreamId}")
    public String loadLiveStreamPage(Model model, @PathVariable int lStreamId){

        LiveStreamDetailVo liveStreamDetailVo = liveStreamService.getLiveStreamDetail(lStreamId);
        log.info("[LIVE] 라이브 방송 정보 : {}  ", liveStreamDetailVo);

        List<LfPackageProductVo> lfPackageProductVos = null;
        if(liveStreamDetailVo.getLfPackageId() != null){
            lfPackageProductVos = liveStreamService.getLiveStreamPackage(liveStreamDetailVo.getLfPackageId());
            log.info("[LIVE] 라이브 방송 패키지 상품 정보 : {}", lfPackageProductVos);
        }
        model.addAttribute("liveStreamDetail", liveStreamDetailVo);
        model.addAttribute("liveStreamProducts", lfPackageProductVos);
        model.addAttribute("lStreamId",lStreamId);
        model.addAttribute("baseUrl", baseUrl);
        return "pages/livestream/livestream";
    }


    @GetMapping("test")
    public String testLiveStream(){
        return "pages/livestream/livestream";
    }
}
