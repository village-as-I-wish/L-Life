package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.repository.MessageRepository;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import kosa.com.suntofu.L_LIFE.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

// Consumer의 topic 처리 파일
// + 2.Kafka Consumer에서 메시지를 받아 MongoDB에 저장
// KafkaMessageConsumer 클래스에서 listen 메서드에서 메시지를 받아 처리하므로, 여기에서 MongoDB에 저장하는 로직을 추가 하기
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;  // MongoDB 저장을 위한 Repository 주입

    @KafkaListener( topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID )
    public void listen(ChatMessageVo message){ // kafka listener에서 듣고 있음, 특정 토픽에서 메시지를 소비하는 Kafka Consumer 역할

        log.debug("Received message from Kafka: " + message);


        // 1. MongoDB에 메시지 저장
        ChatMessageVo chatMessageVo = messageRepository.save(message);
        log.info("chatting message saved {} ", chatMessageVo);
        // 2. 저장된 메시지를 사용자에게 전송
        log.debug("kafka consumer, " + message);
        // livestream/room/{아이디}를 듣고있는 client에 전송
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getLStreamId(), message);
    }
}
