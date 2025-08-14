package com.example.board.domain;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	private final int BLOCK_SIZE = 10;  // 화면에 표시할 개수를 상수로 고정 ! 바꾸고 싶을 때 얘만 고치면 되니까! // 관리하기 편해서 생성한거임 ! 
										// 블록당 표시할 페이지 번호 개수 
	private int StartPage;				// 해당 블록의 시작번호	
	private int endPage;				// 해당 블록의 끝 번호 
	private boolean prev, next;  		// 이전 번호, 다음 번호 (이전 블럭 있냐, 다음 블럭이 있냐 를 담아넣을 거)
	
	private int totalPages;				// 전체 페이지 수 
	private long totalElements;			// 전체 레코드 수 
	private Page<Post> page;            // 페이지 객체 
										// 나중에도 계속 재사용 처리할거면 제네릭으로 해주는게 좋긴 함!, 지금은 우리가 페이징 처리할게 Post만 할거임 나중에 게시판을 더 만들었음
								   		// Post를 이용한 게시판, News 이용한 게시판, Sports 이용한 게시판을 만들고 페이징 처리를 하게 될 경우, Post이 외외에 게시판에 활용할 수가 없음..! 
										// PageDTO 객체를 만들 때, Post, News, Sports로 할 수 있음 - 자료형// 근데 지금은 그냥 할게여  
 
	// 생성자 생성 ! 
	public PageDTO(Page<Post> page) {
		this.page = page;
		this.totalPages = page.getTotalPages();   	  //아까 본거 리턴하게 끔
		this.totalElements = page.getTotalElements(); // 페이지 객체 안에 이 값이 있으니까 꺼내오는거임 
	
		// 현재 페이지 (내가 현재 보고 있는! , 페이지 객체 안에 들어있음)
		int currentPage =  page.getNumber() + 1;   //시작번호 1번이니까! 
		
		this.endPage = (int) (Math.ceil(currentPage / (double)BLOCK_SIZE)) * BLOCK_SIZE;
		this.StartPage = this.endPage - (BLOCK_SIZE - 1);
	
		if( this.totalPages < this.endPage) 
			this.endPage = this.totalPages;
	
		// 둘다 여부를 확인할거라서 ! boolean 
		this.prev = this.StartPage > 1;  // 이전이 있는거 
		this.next = this.endPage < this.totalPages; // 다음이 있는거 
		
	// 객체 생성과 함께 값이 다 할당되도록 설정 
	}
	


}
