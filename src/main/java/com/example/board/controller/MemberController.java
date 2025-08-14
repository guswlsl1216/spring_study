package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;
import com.example.board.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    private final UserController userController;
	@Autowired
	private MemberService memberService;

    MemberController(UserController userController) {
        this.userController = userController;
    }

	// 요청받으면 회원가입 페이지로 넘어가게 해줄거임 (컨트롤러)
	@GetMapping("/auth/join")
	public String join() {
		
		return "user/join";
	}
	

	@PostMapping("/auth/join")
	@ResponseBody                    //응답 파일을 json 형태로 보낼거라서 
	public ResponseDTO<?> insertUser(@RequestBody User user) {
		
		User findUser = memberService.getUser(user.getUsername()); // 중복 검사 메서드 호출 => 중복일 경우 중복 값이 담기고, 없으면 빈객체 값이 담김
		
		if(findUser.getUsername() == null) { //중복되지 않았다면? 
 		memberService.insertUser(user);
		
		return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님 회원가입 성공!");      //https 모음집 , ok의 뽑아내는 숫자가 필요해서 value
	} else {
		return new ResponseDTO<>( HttpStatus.BAD_REQUEST.value(), user.getUsername() + "님은 이미 가입한 회원입니다."); 
	}

}	
	@PostMapping("/auth/join2")  // 자바스크립트 말고 폼 태그로도 요청하고 받을 수 있음
	public String insertUser2(User user, Model model) {
		memberService.insertUser(user);
		
		model.addAttribute("msg", user.getUsername() + "님 회원가입 성공");
		
		return "index";
	}
	
	
	@GetMapping("/auth/login")
	public String login() {
		return "user/login"; // html 리턴 => 경로 써주는거임 
	}
	
	@PostMapping("/auth/login") // 메서드 오버로딩 : 똑같은 메서드가 두개 있어도 매개 변수가 다르면 노 상관,  
	@ResponseBody
	public ResponseDTO<?> login(@RequestBody User user, HttpSession session) {
		// 이 사람이 입력한 아이디와 비번이 데이터 베이스 목록에 있는지 검사해봐야 함 
		
		User findUser = memberService.getUser(user.getUsername()); // 이전에 만들어놧음 => 유저아이디 받아서 있냐 없냐 찾는거 쓸 수 있음 , 코드 재사용  // id 존재 하냐 안하냐
		if(findUser.getUsername() == null) {
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "잘못된 아이디");
		} else {
			if(user.getPassword().equals(findUser.getPassword())) {
				// 로그인 성공 처리 -> 세션에 담아서 저장한 후 계속 유지되게 끔 할거에여 // 세션 : 매개변수에 http 세션을 입력해서 
				session.setAttribute("principal", findUser);
								//사용자 정보에 대한 키. 밸류
			    return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() + "님 로그인 성공");
			} else {
				return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "비밀번호 오류");
			}
					
		}
		 
	}
	
	
	@GetMapping("/auth/logout")
	public String logout(HttpSession session) {
		session.invalidate();   // 로그아웃은 세션을 날려버리면 되가지구! , invalidate가 날려주는 함수! 
		
		return "redirect:/" ; // 처음 초기화면 컨트롤러가 작동하게끔! 
	
	// return "index"로 하게 되면 화면은 초기화면이지만 주소가 logout url로 설정됨
    // Redirect : 클라이언트가 요청한 주소에서 다른 요청 주소로 넘어감 (리디렉션, 리다이렉트)
	}
	
	@GetMapping("/auth/info")
	public String info(HttpSession session, Model model) {
		// 나중에 작업할 때 세션에 담긴 정보는 제한적이라고 가정 ( username만 있다고 칩시다! ) 
		// 지금 코드 상에서는 이렇게까지 할 필요는 없음
		User principal = (User)session.getAttribute("principal");             // seesion에 담아 DB에서 꺼내서
		
		User userInfo = memberService.getUser(principal.getUsername()); 
		
		model.addAttribute("info", userInfo);                                 // 모델에 담아서 화면에 뿌림 // 따라서 회원정보 수정해도, 수정은 됐지만 세션에는 이전 info 내용이 들어가있음  (세션 가지고 변경해라 라는 코드를 쓴 적이 없으니까 )
																			 // 변경 후 로그아웃하면 그제서야 잘나옴! 			
		return "user/info";
	}
 
	// 요청받은 데이터로 수정처리가 되게 끔 ! 
	@PostMapping("/auth/info")
	@ResponseBody
	// 클라이언트가 준 내용 리퀘스트 바디로 User 내용 받아주면 댐 
	public ResponseDTO<?> info(@RequestBody User user, HttpSession session) {
		
		User findUser = memberService.updateUser(user);
		session.setAttribute("principal", findUser); 			// 세션에 담아있던 유저 정보를 덮어쓰기 해주는거임 
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "회원정보 수정완료"); //( 상태 코드와 메세지를 객체로 묶어서 보냄 )
		
	}
	
	@DeleteMapping("/auth/user")
	@ResponseBody // json으로 받을거라서                     //탈퇴해도 로그인되어 있어서 세션 만료시키기 위해 세션 추가로 불러와서 강제로 튕기게 해줌 
	public ResponseDTO<?> delete (@RequestParam int id, HttpSession session) {
		
		memberService.deleteUser(id);
		session.invalidate();
		return new ResponseDTO<>(HttpStatus.OK.value(), "회원 탈퇴 완료");
	} 
}
// 컨트롤러에서 서비스에서 만든 애를 주입받아서 사용할 수 있음 => @service => @memberService  
// ? 넣으면 객체에 맞게 타입이 지정된다고 보면 댐 