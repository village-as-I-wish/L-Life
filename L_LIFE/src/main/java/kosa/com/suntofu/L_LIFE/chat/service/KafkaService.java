package kosa.com.suntofu.L_LIFE.chat.service;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import kosa.com.suntofu.L_LIFE.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
    private final KafkaTemplate<String, ChatMessageVo> kafkaTemplate;

    public void sendMessageToKafka(ChatMessageVo message) {
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
            log.info("kafka producer: {}", message);
        } catch (Exception e) {
            log.error("메시지 전송 중 오류 발생", e);
        }
    }
}