package com.example.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.domain.User;

//JPA에 있는 거를 인터페이스에 상속받을 거임 (첫번째)

// 스프링이 UserRepository하는 객체를 가직 ㅗ있음 
// 이걸 넣어야 스프링한테 레파지토리라고 알려주는거임 
@Repository                                 // 기본적인 쿼리 메서드들은 jpa에 내장되어 있어서 상속 받은 인터페이스를 만들고 
public interface UserRepository extends JpaRepository<User, Integer> {
// spring한테 레파지토리의 인터페이스라는 걸 알려줘야함        // 제너릭 엔티티 클래스 명 // 엔티티 클래스에 들어있는 기본 키 필드의 타입, 자로형을 써주고 
// 간단한 쿼리문들은 다 내장되어 있음, 상속받아서..!         
	
	
// 이 두가지로 연동된 데이터 insert,select 이런거 다 사용할 수 있게 댐! 	


// 인터페이스 - 
// insert, select 작업 모두 userController에서 구현 ! 

//유저 네임 메서드 (중복 검사) // 추상메서드 하나 만들어놓으면 jpa가 만들어줌 select 기능 메서드 
	Optional<User> findByUsername(String username);
	
	
	
	
	
}