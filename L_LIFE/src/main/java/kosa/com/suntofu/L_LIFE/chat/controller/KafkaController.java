package kosa.com.suntofu.L_LIFE.chat.controller;

import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
import kosa.com.suntofu.L_LIFE.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
// "url로 client가 요청이 오면 지정한 topic에 대한 record를 생성하여 전달
public class KafkaController {
    private final KafkaTemplate<String, ChatMessageVo> kafkaTemplate;

    @MessageMapping("/l-life/pub/chat/message") // url로 들어오는 정보 처리
    public void purchase(ChatMessageVo message){

        log.debug("I am producer..");
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);  //  STOMP을 통해 받은 메시지를 Kafka의 특정 토픽으로 메시지를 전송
    }

}