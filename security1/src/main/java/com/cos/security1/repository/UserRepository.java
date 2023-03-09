package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// CRUD 함수를 JpaRepository가 들고 있다.
// @Repository라는 어노테이션이 없어도 loC된다. 이유는 JpaRepository를 상속했기 때문에 !
public interface UserRepository extends JpaRepository<User, Integer>{
	// findBy까진 규칙 -> Username 문법
	// select * from user where username =1? ( 이 쿼리가 자동으로 생성되는 것과 같습니다! ) 
	public User findByUsername(String username);  // Jpa Query methods 참 
	
	// select * from user where email =? 쿼리 자동생성 
//	public User findByEmail();
}
