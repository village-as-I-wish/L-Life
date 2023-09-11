package kosa.com.suntofu.L_LIFE.config;

import kosa.com.suntofu.L_LIFE.member.interceptor.LogInterceptor;
import kosa.com.suntofu.L_LIFE.member.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor()) // LogInterceptor 등록
//                .order(1)	// 적용할 필터 순서 설정
//                .addPathPatterns("/mypage")
//                .excludePathPatterns("/l-life", "/l-life/main"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(new LoginCheckInterceptor()) //LoginCheckInterceptor 등록
                .order(1)
                .addPathPatterns("/subscription/standard/payment_detail")
                .excludePathPatterns("/", "/main", "/member/login", "/css/**", "/js/**", "/html/**", "/img/**", "/stomp");
        ;
    }
}
