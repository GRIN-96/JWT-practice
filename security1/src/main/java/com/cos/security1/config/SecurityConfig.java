package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{  // 스프링 시큐리티 필터 생성
	
	// 해당 메서드의 리턴되는 오브젝트를 loC로 등록해준다. 
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") 
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 권한이 있는사람에게만 접근을부여한다.
			.anyRequest().permitAll()  // 나머지 모든 요청은 접근을 허용한다
			.and()
			.formLogin()
			.loginPage("/loginForm") // 권한이 없는 경우 에러페이지가 아닌 로그인 페이지가 열리도록 해주는 역할을 합니다.
			.loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다 !
			.defaultSuccessUrl("/"); // 성공 후 기본 / 페이지로 !
		
		// 접근 권한이없는 요청을 할 경우 403 ERROR가 발생합니다 !
	}

}
