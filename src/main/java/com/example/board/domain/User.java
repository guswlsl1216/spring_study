package com.example.board.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id  // 기본키 설정해주는 어노테이션 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 회원번호
	//      자료형 필드명 설정해줌 //  
	@Column(nullable = false, length = 50, unique = true)
	private String username; // 아이디
	
	@Column(length = 100)
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 100)
	private String email; // 이메일
	
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}






