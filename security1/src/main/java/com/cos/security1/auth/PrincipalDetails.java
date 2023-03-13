package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킵니다.
// 로그인 진행이 완료가 되면 session을 만들어줍니다. (Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User정보가 있어야된다.
// User오브젝트타입 => UserDetails 타입 객체

// Security Session 영역에 저장되는 타입은 Authentication ! 그 안에는 UserDetails(PrincipalDetails) 객체가 있다.

@Data 
public class PrincipalDetails implements UserDetails, OAuth2User{

	private User user; // composition - 구성
	private Map<String, Object> attributes;
	
	// 일반 로그인 
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	
	// OAuth 로그인 
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	// 해당 User의 권한을 리턴하는 곳 !!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// String type으로 return이 어려우니 변환과정을 거칩니다!
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {  // 계정 만료 유무 
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {  // 계정 잠김 유무 
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {  // 계정비밀번호 사용기간 1년 이상? 
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정활성화 유무 
		
		// 우리 사이트!! 1년동안 회원이 로그인을 하지않았다면 휴면계정으로 하기로 했을때 ?
		// user.getLoginData() 유저의 접속 이력을 가져와서
		// 현재시간 - 로그인시간 => 1년을 초과하면 return false; 식으로 구현가능합니다 !
		
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
	
	
}
