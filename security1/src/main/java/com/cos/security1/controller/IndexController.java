package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //View를 리턴하겠다 !!
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(
			Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) { // DI( 의존성 주입 )   일반 로그인의 경우 userDetails type 사용.
		System.out.println("/test/login ================");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getUser());
		
		System.out.println("userDetails : " + userDetails.getUser());
		return "세션 정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthLogin(
			Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) { // DI( 의존성 주입 )   // 페이스북, 구글과 같은 OAuth로그인 시 OAuth2User type사용.
		System.out.println("/test/login ================");
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication : " + oAuth2User.getAttributes());
		System.out.println("oauth2User : " + oauth.getAttributes());
		
		return "OAuth 세션 정보 확인하기";
	}
	
	// localhost:8080/
	// localhost:8080
	@GetMapping({"","/"})
	public String index() {
		// mustache로 화구
		// mustache 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates (prefix), .mustache (suffix) < pom.xml에 사용지정 되어있으면 생략가능!!
		return "index"; // src/main/resources/templates/index.mustache 경로대로 찾게됩니다!
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) { //
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	// 스프링시큐리티가 해당주소 낚아채버림 - SecurityConfig 파일 생성 후 작동안함.
	@GetMapping("/loginForm")
	public String login() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		// 패스워드는 암호화 한 후 다시 저장해준다.
		user.setPassword(encPassword);
		userRepository.save(user); // 회원가입은 잘된다. 비밀번호 : 1234 => 시큐리티로 로그인을 할 수 없게된다. 이유는 패스워드가 암호화되지 않았기때문 !!
		return "redirect:/loginForm"; // redirect를 붙여줘야 해당 프로젝트의 경로로 이동해줍니다 !
	}
	
	@Secured("ROLE_ADMIN")  // 하나만 줄때는 secured사용합니다 !
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")  // 두개이상 사용시 preAuthorize hasRole 이용합니다 !
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}

}