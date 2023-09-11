package kosa.com.suntofu.L_LIFE.chat.controller;

import kosa.com.suntofu.L_LIFE.chat.service.ChatRoomService;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatRoomVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 목록 조회
//    @GetMapping("/rooms")
//    public ModelAndView rooms(){
//        ModelAndView mv = new ModelAndView("pages/chat/chat");
//
//        mv.addObject("list", chatRoomService.findAllRooms());
//
//        return mv;
//    }

    //채팅방 조회
    @GetMapping("/room/{lStreamId}")
    public String getRoom(@PathVariable int lStreamId, Model model){
        ChatRoomVo roomId = chatRoomService.findRoomById(lStreamId);
        model.addAttribute("roomId", roomId);
        log.info("# get Chat Room, roomID {} ", roomId);
        return "pages/chat/chat";
    }
}
