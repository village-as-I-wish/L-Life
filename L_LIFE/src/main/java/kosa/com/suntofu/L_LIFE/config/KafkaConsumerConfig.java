package kosa.com.suntofu.L_LIFE.config;

import com.google.common.collect.ImmutableMap;
import kosa.com.suntofu.L_LIFE.chat.vo.ChatMessageVo;
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
    ConcurrentKafkaListenerContainerFactory<String, ChatMessageVo> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageVo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    // consumerFactory: Kafka Consumer 설정 생성
    public ConsumerFactory<String, ChatMessageVo> consumerFactory() {
        JsonDeserializer<ChatMessageVo> deserializer = new JsonDeserializer<>(ChatMessageVo.class);
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), deserializer);
    }


    @Bean
    public Map<String, Object> consumerConfigurations() {
        JsonDeserializer<ChatMessageVo> deserializer = new JsonDeserializer<>();
        // 패키지 신뢰 오류로 인해 모든 패키지를 신뢰하도록 작성
        deserializer.addTrustedPackages("*");

        return ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BROKER)  //브로커 알려주기
                .put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class) //key 설정
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)   // value 설정
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                //  consumer가 연결되고 나서 broker가 가진 partion에서 어떤 offset 부터 확인할지
                // (latest : 연결 이후 들어온 값만 소비. earliest : 무조건 partition의 제일 앞부터 확인해서 소비)
                .build();
    }
}