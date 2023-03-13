package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	// 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수 
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인 했는지 확인가능합니다 !
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code return(OAuth-Client라이브러리) -> AccessToken요청
		// userRequest 정보 -> 회원 프로필 받아야함(loadUser 함수) -> 구글로부터 회원 프로필 받아줍니다.
		System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes()); // 회원 프로필이 출력됩니다 ! 구글아이디 , 이메일, 이름 등등..
		
		String provider = userRequest.getClientRegistration().getClientId(); // google
		String providerId = oauth2User.getAttribute("sub");  // Id
		String username = provider + "_" + providerId; // google_109742856182916427686
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oauth2User.getAttribute("email");  // Id
		String role = "ROLE_USER";
		
		// 해당 아이디로 회원가입이 되어있는지 확인
		User userEntity = userRepository.findByUsername(username);
		
		// 회원가입을 강제로 진행시킬 예정 
		if (userEntity == null) {  // 유저 정보가 없을 경우  -> 강제 회원가입 !!
			System.out.println("구글 로그인이 최초입니다.");
			userEntity = User.builder()  // DB에 추가하기 위한 유저정보가 담긴 객체 생성 !
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			
			userRepository.save(userEntity);  // 위에서 만든 유저 객체를 DB에 추가 !
		}else {
			System.out.println("구글 로그인을 이미 한적이 있습니다. 당신은 자동 회원가입이 되어 있습니다.");
		}
		
		// OAuth2User 리턴 
		return new PrincipalDetails(userEntity, oauth2User.getAttributes()); // 유저정보가 담긴 PrincipalDetails 객체 생성 -> 세션에 저장 
	}
}
