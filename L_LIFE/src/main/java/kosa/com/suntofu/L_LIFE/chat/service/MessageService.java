package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.repository.MessageRepository;
import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageVo findById(String id) {
        Optional<MessageVo> messageOptional = messageRepository.findById(id);
        return messageOptional.orElse(null);
    }

    public MessageVo save(MessageVo messageVo) {
        return messageRepository.save(messageVo);
    }

    public void deleteById(String id) {
        messageRepository.deleteById(id);
    }
}
