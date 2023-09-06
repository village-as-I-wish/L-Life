package kosa.com.suntofu.L_LIFE.livestream.controller;

import kosa.com.suntofu.L_LIFE.config.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Api(tags = { "00. Kafka" })
@RestController
@RequestMapping(value = "/api/v1")
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    //@ApiOperation(value = "메시지 전송", notes = "Kafka 메시지를 전송합니다.")
    @PostMapping("/kafka")
    public String sendMessage(@RequestParam("message") String message) {
        this.producer.sendMessage(message);

        return "success";
    }
}
