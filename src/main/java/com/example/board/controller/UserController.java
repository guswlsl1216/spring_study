package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.RoleType;
import com.example.board.domain.User;
import com.example.board.exception.BoardException;
import com.example.board.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
// RequireArgsConstructor => final 상수들 
public class UserController {

	//의존성 주입 
	//접근     자료형           뱐수명 (그대로 null 값이니까 new aaaa(); 이걸 했는데 이미 스프링이 들고 있음 
	
	@Autowired   //의존성 주입 
	private UserRepository userRepository;  

	
	// 회원가입 (id, 비밀번호, 이메일을 입력하면 데이터 베이스에 저장해주고 저장됐으면 완료했습니다! 띄워줄거)
	@PostMapping("/user")
	public String insertUser(@RequestBody User user) {   // 유저 객체로 받고  // json으로 보내면 유저객체로 받을 수 있게끔!  
		
		user.setRole(RoleType.USER);
		
		//  주입 후 객체를 세이브하라고 해볼거임 
		
		userRepository.save(user);
		
		return user.getUsername() + "회원가입 성공";
		
	}
	
	//get Data => 아래에 User(id=0, username=zxczc, password=a1a2, email=ppp@kakao.com, role=null, createDate=null) 로 객체로 들어옴 ! 


	// 회원 조회 ( 회원의 id를 받아서 정보를 보여주는 기능 )
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable int id) {
		
		// select * from users where id=? // 검색할 필드명이 id라고 해서 id가 아니라 // 기본키 설정 
		User findUser = userRepository.findById(id).orElseThrow( () -> {
			return new BoardException(id + "번 회원은 없습니다.");
		}); 													    // get으로 바로 꺼낸거!
																	// select처리 해주는 메서드인데 얘가 select 문을 처리했을 때 결과가 있을 수도 없을 수도 있음.. 
																	// 옵셔널 객체에서 조회된 결과를 꺼내와라! 있으면 꺼내오는데 없으면 예외가 발생되어버림..! 그래서 세세하게 처리해 주는게 좋음..! 				
																	// .get까지 리턴시켜주고 있으면 꺼내고 없으면 예외 발생시켜버림 (optional 내장 함수 )	
		
		return findUser; 
		
		//검색된 결과를 리턴해 줘야함 // 리턴 타입은 optional을 가지고 있음, 검색된 결과가 없을 수도 있어서 검색된 결과가 들어있는지 없는지도 ㅇㅇ 
		// 결과를 findUser에 넣어주세염 // 객체 형태로 
		// 결국 위에 얘기는 메서드로 select 작업한거임 ! 
	}

	 // *  회원 정보 수정! 데이터 베이서 상에서는 update가 되게 끔 처리할거임! 
	 // 정보를 수정할 회원번호, 아이디, 비밀번호, 이메일을 포함해서 요청 
	 // 요청받은 정보를 이용해서 update 작업이 처리되도록 구현 
	
	
	@PutMapping("/user")                  // 여기에 수정할 데이터가 들어가있을거임
	public String updateUser(@RequestBody User user) {
										// 데이터를 보낼거임, 변경할 데이터가 user에 들어있음 // 이미 데이터 베이스에 저장된 거를 바꿔주세요로 할거임
		
		//findUser : DB에 저장되어 있던 원본 데이터 
		// 멤버 변수의 값을 설정해 주는게 setter => set 메서드를 이용해서 유저 정보를 바꿔라! 
		
		//select 작업 먼저
		User findUser = userRepository.findById(user.getId()).orElseThrow( () -> {
			return new BoardException(user.getId() + "번 회원은 없음");
			
		});
		
		findUser.setUsername(user.getUsername() );
		findUser.setPassword(user.getPassword() );
		findUser.setEmail(user.getEmail() );
	
		userRepository.save(findUser);
		return "회원 정보 수정 완료";
	
	}
	
	// 삭제
	// 회원번호를 클라이언트에게 받아서 그 번호인 레코드를 삭제 (탈퇴 기능)
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {    //@PathVariable 조회할 때 썼었움 
 		// 기본키를 기준으로 검색문 처리해 주는 메서드 : findById
		// 기본키를 기준으로 검색해서 삭제 처리해주는 메서드 : deleteByID
		
		userRepository.deleteById(id);
		
		return "삭제 완료";
	}
	
	// 전체 레코드 조회 =>findAll ( select * from )  => 전체 레코드 조회해주는 검색문  
	@GetMapping("/user/list")
	public List<User> getUserList() {
		
		List<User> users = userRepository.findAll(); 
		return users;
	}
}
