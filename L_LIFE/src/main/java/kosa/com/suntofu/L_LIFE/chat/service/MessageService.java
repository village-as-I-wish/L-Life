package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.repository.MessageRepository;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public ChatMessageVo findById(String id) {
        Optional<ChatMessageVo> messageOptional = messageRepository.findById(id);
        return messageOptional.orElse(null);
    }

    public ChatMessageVo save(ChatMessageVo messageVo) {
        return messageRepository.save(messageVo);
    }

    public void deleteById(String id) {
        messageRepository.deleteById(id);
    }

    public List<ChatMessageVo> findBylStreamId(int lStreamId) {
        return messageRepository.findBylStreamId(lStreamId);
    }


}
