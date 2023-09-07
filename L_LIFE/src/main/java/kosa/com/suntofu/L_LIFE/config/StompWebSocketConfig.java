//package kosa.com.suntofu.L_LIFE.config;
//
//import kosa.com.suntofu.L_LIFE.chat.handler.StompHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@RequiredArgsConstructor
//@EnableWebSocketMessageBroker // Stomp를 사용하기 위해 선언하는 어노테이션
//@Configuration
//public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final StompHandler stompHandler;
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws/chat")
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
//    }
//
//    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/pub"); // Client에서 SEND 요청을 처리
//        registry.enableSimpleBroker("/sub"); // 해당 경로로 SimpleBroker를 등록 (SimpleBroker는 해당하는 경로를 sub하는 Client에게 메세지 전달)
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }
//}