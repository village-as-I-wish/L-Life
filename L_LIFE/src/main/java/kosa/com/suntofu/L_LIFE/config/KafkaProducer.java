package kosa.com.suntofu.L_LIFE.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final String TOPIC = "Topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        System.out.printf("Produce message : %s%n", message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}