package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	// 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인 했는지 확인가능합니다 !
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
		// 구글로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code return(OAuth-Client라이브러리) -> AccessToken요청
		// userRequest 정보 -> 회원 프로필 받아야함(loadUser 함수) -> 구글로부터 회원 프로필 받아줍니다.
		System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes()); // 회원 프로필이 출력됩니다 ! 구글아이디 , 이메일, 이름 등등..
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		// 회원가입을 강제로 진행해볼 겁니다 !
		return super.loadUser(userRequest);
	}
}
