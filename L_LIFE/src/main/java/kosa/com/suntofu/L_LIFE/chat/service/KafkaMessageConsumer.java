package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.repository.MessageRepository;
import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
import kosa.com.suntofu.L_LIFE.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

// Consumer의 topic 처리 파일
// + 2.Kafka Consumer에서 메시지를 받아 MongoDB에 저장
// KafkaMessageConsumer 클래스에서 listen 메서드에서 메시지를 받아 처리하므로, 여기에서 MongoDB에 저장하는 로직을 추가 하기

@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;  // MongoDB 저장을 위한 Repository 주입

    @KafkaListener( topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID )
    public void listen(MessageVo message){ // kafka listener에서 듣고있음, 특정 토픽에서 메시지를 소비하는 Kafka Consumer 역할

        // 1. MongoDB에 메시지 저장
        messageRepository.save(message);

        // 2. 저장된 메시지를 사용자에게 전송
        System.out.println("kafka consumer.. ");
        System.out.println(message);
        simpMessagingTemplate.convertAndSend("/l-life/sub/chat/room/" + message.getChatroomId(), message); // livestream/room/{아이디}를 듣고있는 client에 전송
    }
}
