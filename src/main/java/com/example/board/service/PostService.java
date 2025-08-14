package com.example.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.repository.PostRepository;

@Service
public class PostService {
	@Autowired  			// 서비스는 레파지토리와 관련된 기능이 없으니까 의존성 주입 시켜주고 넘기는거임 
	private PostRepository postRepository;
							// 게시글이랑, 작성자 정보 같이 받을거임! // 그래야 같이 저장할거니깐 DB에  
	public void insertPost(Post post, User writer) {
		
								// 포스트 테이블에 게시물 내용을 추가하려고 하는거임 // 
		post.setUser(writer);   // user=null 이라고 되어 있는거를 채워주기 위해서  // 객체 생성 할 때 원시 데이터 타입은 초기값 다 0으로 들어감 (Integer도 null로 들어감)// 참조형은 null   
		post.setCnt(0);	
		
		postRepository.save(post);
	}
	
	// 게시글 전체 목록을 가져와 주는 메서드 => 컨트롤러로 가! 
	@Transactional(readOnly = true)  // 읽기 전용 => 변경 사항이 있든 없든 감지 안함 
 	public Page<Post> getPostList (Pageable pageable) {
		
		return postRepository.findAll(pageable);
		}
	
// List는 페이지에 대한 정보를 담을 수 없어서 얘를 객체로 만들거임 Page 형태로 리턴시켜줌   
// findAll이 전체를 꺼내주는 메서드인데 옵션을 설정할 수 있음 (	정렬 -> 매개변수로 오버라이딩할 수 있음)
// 리턴 타입을 페이지로 
// 컨트롤러에서 받기 위해서 pageable이라는 객체를 만든거임! 

	public Post getPost(int id) {
		Optional<Post> op = postRepository.findById(id);
		
		return op.get();
	}


	public Post putPost(int id, Post post) {
		
		Optional<Post>prePost = postRepository.findById(id);
		
		
//		prePost.setTitle(prePost.getTitle());
//		prePost.setContent(post.getContent());
//		prePost.postRepository.save(post);
		return post;
	}
	
	@Transactional
  public Post updatePost(Post post) {
	  
	  Post originPost = getPost(post.getId());
	  
	  originPost.setTitle(post.getTitle());
	  originPost.setContent(post.getContent());
  
	  postRepository.save(originPost);
  
	  return originPost;
  // 아 이 만든걸 보내줘야 하니깐 컨트롤러에서 쓸 수 있게 originPost 내보내줌 ! 
  }
	
	
}
