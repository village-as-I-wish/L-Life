package kosa.com.suntofu.L_LIFE.livestream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/livestream")
public class LiveStreamController {


    /**
     *
     * @return String
     */
    @GetMapping("/{lStreamId}")
    public String loadLiveStreamPage(Model model, @PathVariable int lStreamId){
        model.addAttribute("lStreamId",lStreamId);
        return "pages/livestream/livestream-onboard";
    }


    @GetMapping("test")
    public String testLiveStream(){
        return "pages/livestream/livestream";
    }
}
