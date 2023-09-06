package kosa.com.suntofu.L_LIFE.livestream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/livestream")
public class LiveStreamController {


    /**
     * 라이브 방송 화면 페이지 로드
     * @return String
     */
    @GetMapping("")
    public String loadLiveStreamPage(){
        return "pages/livestream/livestream-onboard";
    }


    @GetMapping("test")
    public String testLiveStream(){
        return "pages/livestream/livestream";
    }
}
