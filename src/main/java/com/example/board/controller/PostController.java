package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.board.BoardApplication;
import com.example.board.domain.PageDTO;
import com.example.board.domain.Post;
import com.example.board.domain.ResponseDTO;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;


import jakarta.servlet.http.HttpSession;


@Controller  // 컨트롤러 역할을 수행하는 애니까! 
public class PostController {

    private final BoardController boardController;

    private final BoardApplication boardApplication;

	@Autowired  // 서비스 파일에서 만든거 큰트롤러가 메서드를 받아서 써야하니까 의존성 주입 또 해줌 
	private PostService postService;


    PostController(BoardApplication boardApplication, BoardController boardController) {
        this.boardApplication = boardApplication;
        this.boardController = boardController;
    }

	
	@GetMapping("/post/insert")
	public String insert() {
		return "post/insertPost";
	}
	// 페이지 이동 ! 


	@PostMapping("/post")
	@ResponseBody                       //post 객체 만들어서 받아주세욤  //작성자 정보는 session에 들어 있어서 걍 꺼내서 쓰면 댐 
	public ResponseDTO<?> insertPost(@RequestBody Post post, HttpSession session) {
//	System.out.println(post.toString());
	
		User writer = (User)session.getAttribute("principal");    // session에서 받을 거를 변수로 만들어준다! 그리고 평변환  
				
//	System.out.println(writer.toString());
//		System.out.println(post.toString());
		
		
		//포스트 서비스에서 준 메서드를 사용해서 받은 거 넣어주기
		postService.insertPost(post, writer);
		// 잘 저장되어 있으니까 응답 객체만 보내주면 됨
		
		return new ResponseDTO<>(HttpStatus.OK.value(), "새 게시글 등록 완료");
	}
	
	@GetMapping("/")  // 인덱스에서 전체 게시글 목록 ! => 모델에 전체 목록이 들어가서 => 렌더링 시켜줄 수 있음 
	// 요청이 들어오면 실행이 되는건데 매개변수쪽에 페이지 에이블이라는
	// 사람들이 몇번째 페이지 좀 보여주세여! 라고 하게 시킬거임 ( 클라이언트는 페이지에 대한 정보를 보내라 )) => 그 정보를 받는 객체를 Pageable로 설정
	// 요청할 때 몇번째 페이지 보고 싶다고 요청하면 => 페이지에 대한 내용만 model에 담아서 내보내게 할거임!
	
										// 페이지에 대한 정보를 안보내주면 이거는 기본 값이라는 어노테이션 입력 (기본값) // 사이즈는 한 페이지에 보여줄 개수 //sort는 정렬 기준  
										// id 기준으로 정렬해줘! sort 없으면 정렬 안함 ! (정렬 기준은 기본값이 오름 차순, direction으로 정렬 기준 내림 차순 ) 
										// 페이지 번호를 받아줄 수 있음 Page로 ! // 바꿀거 있으면 쓰고 // 기본 값 그대로 쓸거면 안써도 됨 
																										// 아래 설정을 가지고 있는 객체입니다! findAll로 보내면 그걸 토대로 select 작업을 함 
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Post> list = postService.getPostList(pageable);
		//리턴 타입도 페이지로! 
		model.addAttribute("pageDTO", new PageDTO(list));
		model.addAttribute("postList", list); 
		
		//Page 객체가 가지고 있는 정보들 
		List<Post> l = list.getContent(); // 실제 데이터는 content에 있음 
		
//		System.out.println("전체 페이지 수 : " + list.getTotalPages());
//		System.out.println("전체 레코드 수 : " + list.getTotalElements());
//		System.out.println("전체 페이지 번호 : " + list.getNumber());
//		System.out.println("페이지 당 표시할 개수 : " + list.getSize() );
//		System.out.println("페이지에 데이터가 있나 없나 : " + list.hasContent());
//		System.out.println("이전 페이지가 있냐 없냐 : " + list.hasPrevious());
//		System.out.println("다음 페이지가 있냐 없냐 :" + list.hasNext());
//		System.out.println("첫번째 페이지냐? : " + list.isFirst());
//		System.out.println("마지막 페이지냐? : " + list.isLast());
		
		return "index"; 
		
		
		// return "index" 파일명만 쓰면 html 리턴해줌! (index.html로 인식) 
		// 데이터를 꺼내고 싶으면 그냥 
	
	}
	
	@GetMapping("/post/{id}")
						//클라이언트가 보낸 번호가 id에 들어감 //그거 데이터베이스에서 select 해서 꺼내오면 댐   
	public String post(@PathVariable int id, Model model) {
		
		Post post = postService.getPost(id);
		
		model.addAttribute("post", post); //get post html에서 "post"이름으로 post 내용을 쓸 수 있음.
		
		
		return "post/getPost";
	}
	
	// 요청을 해야 처리할 메서드가 생김 : 요청 ! 
	@GetMapping("/post/update/{id}")
	public String update(@PathVariable int id, Model model) {
		
		Post post = postService.getPost(id);
		
		model.addAttribute("post", post);   // 넘어가는 건 상세 페이지 들어가는 거랑 똑같고 
											// 전에 했던거 불러와져 있어야 하니까 model에 담아서 똑같이 쓰는거임

		return "post/updatePost"; // 랜더링 시켜라! 
		// return "html 파일 경로와 파일 명" // 
	}

	// 페이지 리턴이 아니라 응답 데이터를 json으로 보내줘야 함! 
	
	@PutMapping("/post")
	@ResponseBody							//post 객체를 생성해서 요청들어온 걸 받을 수 있도록 
											// 매개 변수에 수정할 제목과 내용이 있을거임 
											// 얘는 세션 필요 없음, 세션 활용 안하니까 , http 세션 안불러와도 됨 다른거임 
	public ResponseDTO<?> update (@RequestBody Post post)  {
		
		postService.updatePost(post);
		
	return new ResponseDTO<>(HttpStatus.OK.value(), post.getId() + "게시글 수정 완료");
	}
	
	@Autowired
	private PostRepository postRepository;
	
	@DeleteMapping("/post/{id}")
	@ResponseBody
	public ResponseDTO<?> delete(@PathVariable int id) {
	postRepository.deleteById(id);
	
	return new ResponseDTO<>(HttpStatus.OK.value(), id + "번 게시글 삭제 완료");
		
	}
	

}
	
	
	


