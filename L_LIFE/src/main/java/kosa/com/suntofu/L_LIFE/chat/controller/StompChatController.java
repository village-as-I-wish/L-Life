package kosa.com.suntofu.L_LIFE.chat.controller;

import kosa.com.suntofu.L_LIFE.chat.service.KafkaService;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {
    private final SimpMessagingTemplate template;

    // KafkaService 의존성 주입
    private final KafkaService kafkaService;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageVo messageVo){
        messageVo.setMessage(messageVo.getMName() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + messageVo.getLStreamId(), messageVo);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageVo messageVo){
        log.info("Kafka로 메시지 전송 messages {} ", messageVo);
        //template.convertAndSend("/sub/chat/room/" + messageVo.getLStreamId(), messageVo);

        // Kafka로 메시지 전송
        kafkaService.sendMessageToKafka(messageVo);
    }
}
