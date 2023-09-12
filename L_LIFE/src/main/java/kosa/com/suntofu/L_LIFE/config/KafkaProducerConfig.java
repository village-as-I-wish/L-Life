package kosa.com.suntofu.L_LIFE.config;
import com.google.common.collect.ImmutableMap;
import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
import kosa.com.suntofu.L_LIFE.constant.KafkaConstants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@EnableKafka
// client -> server 로 요청이 올 때 path에 따라 처리할 로직
public class KafkaProducerConfig {

    @Bean
    // ProducerFactory: 카프카 서버로 메시지를 보내는 생산자를 생성
    public ProducerFactory<String, MessageVo> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    // 생산자의 기본 설정 (브로커의 주소, 키와 값의 직렬화 방식)
    public Map<String, Object> kafkaProducerConfiguration() {
        return ImmutableMap.<String, Object>builder()
                .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BROKER)      // 브로커 알려주기
                .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)  // key 문자열, 토픽의 파티션 지정할때 사용됨
                .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)  // value JSON 형식
                .build();
    }

    @Bean
    // KafkaTemplate는:  카프카 서버로 메시지를 전송하는 API를 제공
    public KafkaTemplate<String, MessageVo> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}