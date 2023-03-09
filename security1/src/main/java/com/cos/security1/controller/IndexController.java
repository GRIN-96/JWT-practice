package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //View를 리턴하겠다 !!
public class IndexController {
	
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
	public @ResponseBody String user() {
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
	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public @ResponseBody String join() {
		return "join";
	}
	
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨!!";
	}
}