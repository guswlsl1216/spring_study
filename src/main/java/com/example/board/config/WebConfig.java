package com.example.board.config;
// 전체 환경 설정 클래스

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 						// 얘한테도 또 다양한 추상 메서드들이 있음
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new AuthInterceptor())
				.addPathPatterns("/");
	
	}
}
