package com.example.imgTest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class security {
    
    /*
     * WebMvcConfigurer: Spring MVC 설정을 커스텀할 때 사용하는 인터페이스
     *** \운영 환경에서는 * 대신 특정 도메인만 허용하는 것이 보안상 좋음\
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // 프론트엔드 주소, http://localhost:3000에서 오는 요청만 허용(여러 개도 설정 가능)
                        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE") // 허용할 HTTP 요청 메서드를 지정
                        .allowedHeaders("*") // 요청 시 사용할 수 있는 HTTP 헤더를 설정, 특정 헤더만 허용하고 싶다면>.allowedHeaders("Authorization", "Content-Type") authorization은 JWT 토큰을 사용할 때 필요
                        .allowCredentials(true); // 쿠키, 인증 정보(Credentials)를 포함한 요청을 허용
            }
        };
}
}