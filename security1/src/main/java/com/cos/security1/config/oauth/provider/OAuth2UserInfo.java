package com.cos.security1.config.oauth.provider;

public interface OAuth2UserInfo {
	String getProviderId(); // id
	String getProvider(); // facebook 
	String getEmail();  // email 저장
	String getName();  // name 저장
}
