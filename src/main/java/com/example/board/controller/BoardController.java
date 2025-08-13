package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  //rest로 하면 html을 리턴해줄 수 가 없어서 그냥 컨트롤러! 
public class BoardController {

	@GetMapping("/")
	public String index() {
		return "index"; // 파일명만 쓰면 html 리턴해줌! (index.html로 인식) 
		
	
	}
}
