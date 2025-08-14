package com.example.board.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id   // 아이디 키 설정 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // 일련번호 
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;

	@Lob   // 라지!, 오브젝트 커다란 데이터 저장해줘! 
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	private Timestamp crateDate;
	
	private int cnt;   // 기본값 설정 안하면 알아서 초기값 0으로 들어지니깐 
	
	// 양방향 참조
	// 단방향 참조
	
	//작성자       게시글 관계 지정 ! 
	@ManyToOne(fetch = FetchType.EAGER)  //lazy가 필요할 때, eager는 동시에 다 가져옴 
	@JoinColumn(name = "userid")
	private User user;

}
