package com.example.board.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice  // 컨트롤러에서 발생하는 모든 예외를 처리해주는 // 컨트롤러에서 발생하는 공통 예외 처리해 주는 클래스다! 
@RestController 
public class BoardExceptionHandler {

	@ExceptionHandler(value = Exception.class)  // 모든 발생되는 예외의 부모 (얘를 상속받는 자식 예외가 있는거임) 모든 예외 발생 작동 시 아래에 있는 메서드가 실행되는거임 
	public String globalExceptionHandler(Exception e) {
		return "<h1>" + e.getMessage() + "</hi>";
 		
		
// 그냥 메서드 하나 만든거임! 메서드 명 ! (예외 발생 볼 거라서 그냥 이름 그렇게 지은거임) 
// 다른 컨트롤러에서 예외가 발생되면 exception 객체가 생겼다는 거임! 
// @ControllerAdvice  어노테이션 하나로 다 처리가 가능함! 관점 지향 프로그래밍! 		
	}
	
}
