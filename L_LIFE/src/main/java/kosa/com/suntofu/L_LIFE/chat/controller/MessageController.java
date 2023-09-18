package kosa.com.suntofu.L_LIFE.chat.controller;

import kosa.com.suntofu.L_LIFE.chat.service.MessageService;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ChatMessageVo create(@RequestBody ChatMessageVo message) {
        return messageService.save(message);
    }

    @GetMapping("/{id}")
    public ChatMessageVo findById(@PathVariable String id) {
        return messageService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        messageService.deleteById(id);
    }
}
