package kosa.com.suntofu.L_LIFE.config;

import com.google.common.collect.ImmutableMap;
import kosa.com.suntofu.L_LIFE.chat.vo.MessageVo;
import kosa.com.suntofu.L_LIFE.constant.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    // kafkaListenerContainerFactory: 카프카가 동시에 여러 메시지를 처리할 수 있도록 돕는 역할
    ConcurrentKafkaListenerContainerFactory<String, MessageVo> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageVo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    // consumerFactory: Kafka Consumer 설정 생성
    public ConsumerFactory<String, MessageVo> consumerFactory() {
        JsonDeserializer<MessageVo> deserializer = new JsonDeserializer<>(MessageVo.class);
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), deserializer);
    }


    @Bean
    // Kafka Consumer의 설정(브로커의 주소, 키와 값의 역직렬화 방식, 그룹 ID 등)
    public Map<String, Object> consumerConfigurations() {

        return ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BROKER)  //브로커 알려주기
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class) //key 설정
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new JsonDeserializer<>(MessageVo.class))   // value 설정
                .put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                //  consumer가 연결되고 나서 broker가 가진 partion에서 어떤 offset 부터 확인할지
                // (latest : 연결 이후 들어온 값만 소비. earliest : 무조건 partition의 제일 앞부터 확인해서 소비)
                .build();
    }
}