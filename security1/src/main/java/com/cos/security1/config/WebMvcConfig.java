package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8"); // view의 인코딩 UTF-8
		resolver.setContentType("text/html; charset=UTF-8"); // 데이터 전달방식은 html
		resolver.setPrefix("classpath:/templates/"); // 프로젝트 위
		resolver.setSuffix(".html"); // .html로 해두어야 mustache로 인식할 수 있게 됩니다.
		
		registry.viewResolver(resolver);
	}
	
	
}
