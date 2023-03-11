package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;

// 1. 코드받기(인증), 2.엑세스토큰(권한), 3. 사용자프로필 정보를 가져옴, 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 합니다 !
// 4-2 (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip등급, 일반등급) 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  // secured 어노테이션 활성화 / preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{  // 스프링 시큐리티 필터 생성
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	// 해당 메서드의 리턴되는 오브젝트를 loC로 등록해준다. 
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()  // 인증만 되면 들어갈 수 있는 주소 !!!!! 
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") 
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 권한이 있는사람에게만 접근을부여한다.
			.anyRequest().permitAll()  // 나머지 모든 요청은 접근을 허용한다
			.and()
			.formLogin()
			.loginPage("/loginForm") // 권한이 없는 경우 에러페이지가 아닌 로그인 페이지가 열리도록 해주는 역할을 합니다.
			.loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다 !
			.defaultSuccessUrl("/") // 성공 후 기본 / 페이지로 !
			.and()
			.oauth2Login()
			.loginPage("/loginForm")  // 구글 로그인이 완료된 뒤의 후처리가 필요합니다!  Tip. 코드 X, (엑세스토큰 + 사용자프로필정보 O)
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
		
		// 접근 권한이없는 요청을 할 경우 403 ERROR가 발생합니다 !
	}

}
