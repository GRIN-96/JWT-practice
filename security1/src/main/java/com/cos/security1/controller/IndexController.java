package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}