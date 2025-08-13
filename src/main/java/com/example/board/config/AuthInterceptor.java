package com.example.board.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.board.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 스프링에 있는 인터페이스임! (전이냐 후냐 선택해서 하게 끔 추상 메서들이 만들어져 있음)
public class AuthInterceptor implements HandlerInterceptor {

	@Override						//요청 객체              // 응답 객체                  // 핸들러에 대한거
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		HttpSession session = request.getSession();   // 원래 여기서 들어오는걸 꺼내 쓴거임
		
	 User principal = (User)session.getAttribute("principal");    // 현재 로그인한 사람의 정보가 있으면 꺼내고 없으면 null이 나옴 (아까 세션에 설정했던 키 )  
	 															// 다형성 때문에 얘는 오브젝트로 리턴댐 => User로 형변환 함				
	 	if(principal == null) {
	 		response.sendRedirect("/auth/login");
	 	}
	 // 로그인 안한사람은 로그인 페이지로 튕겨나감  
	 	return true;
	} 
	// 세션을 가져와서 로그인한 사용자 정보 꺼내라고 함 => 있으면 현재 로그인 중 , 없으면 로그인 안함 
	// if문으로 로그인 안했으면 강제로 로그인 화면으로 넘겨버림
	// 로그인한 사용자만 컨트롤러로 넘어가지게 해줌 // return true * 이 메서드의 리턴타입이 ture, false라서 
	
	
}
