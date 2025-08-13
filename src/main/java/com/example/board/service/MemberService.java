package com.example.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.domain.RoleType;
import com.example.board.domain.User;
import com.example.board.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service //컨트롤러처럼 , 어노테이션 넣어주면 스프링이 알아서 객체로 관리함! 
public class MemberService {

// 컨트롤러에서 (클라이언트가 주는)회원 가입 정보를 받아와서 
//권한을 부여(하는 비지니스 로직이)하고 DB에 삽입해주는 메서드를 만들거임 
	
	@Autowired
	private UserRepository userRepository;
							
	@Transactional
	public void insertUser(User user) { //정보 받아온게 여기 매개변수! 
		
		user.setRole(RoleType.USER);    // 관리자가 들어오면 admin으로 바꿔주면 댐 ! 
		
		userRepository.save(user);		// 다시 레파지토리에 넘겨줘야함 => 의존성 주입 (@Autowired)  // private UserRepository userRepository;
		
	}
	
	// username을 이용해서 DB 조회 
	public User getUser(String username) {
		// select 검색 결과가 있으면 해당 내용이 findUser에 담기고,
		// 없으면 new User() 생성된 빈객체가 findUser에 담김  				// 있으면 있는걸 리턴, 없으면 아래 코드에 대한 내용을 리턴해줌
		User findUser = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		
		return findUser;
	}
	
	public User updateUser(User user) {
		// 데이터 베이스에서 꺼내오는 과정 <id가 옵셔널! >
			User findUser = userRepository.findById(user.getId()).get();    // 원본 꺼내오고
			
			findUser.setUsername(user.getUsername() );
			findUser.setPassword(user.getPassword() );						// 변경하고		
			findUser.setEmail(user.getEmail() );
		
			userRepository.save(findUser);								   // 다시 집어넣고  
		// 업데이트가 없어서 세이브를 따로 해줘야함
	  return findUser;
	}

	// 그래서 리턴 타입을 바꾸고 코드에 바꿔줄거임 
	
	// 컨트롤러에서 삭제할 아이디를 쏴주면 서비스에서 삭제하게 끔 처리해줄거임
	// delete 메서드 조건 id로 걸어서 ! // finfBy는 select  
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
	

}
