package kosa.com.suntofu.L_LIFE.chat.controller;

import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
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
    private final KafkaTemplate<String, MessageVo> kafkaTemplate;

    @MessageMapping("/l-life/pub/chat/message") //   url로 들어오는 정보 처리
    public void purchase(MessageVo message){

        log.debug("I am producer..");
        kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
    }

}