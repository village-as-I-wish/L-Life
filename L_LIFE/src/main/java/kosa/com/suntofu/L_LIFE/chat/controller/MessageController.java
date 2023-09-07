package kosa.com.suntofu.L_LIFE.chat.controller;

import kosa.com.suntofu.L_LIFE.chat.service.MessageService;
import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{id}")
    public MessageVo findById(@PathVariable String id) {
        return messageService.findById(id);
    }

    @PostMapping
    public MessageVo create(@RequestBody MessageVo messageVo) {
        return messageService.save(messageVo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        messageService.deleteById(id);
    }
}
