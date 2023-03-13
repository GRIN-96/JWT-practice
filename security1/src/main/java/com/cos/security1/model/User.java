package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  // 기본 생성자를 생성해주는 어노테이션 입니다.
public class User {
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Pk를 autoincrement 해주는 어노테이션입니다!
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	
	private String provider;
	private String providerId;  // 구글에서 pk로 주는 id
	@CreationTimestamp
	private Timestamp createDate;
	
	
	// 생성자 함수
	@Builder   // entity에 정보를 추가하기 위한 어노테이션 
	public User(int id, String username, String password, String email, String role, String provider, String providerId,
			Timestamp createDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}
	
	
	
}
