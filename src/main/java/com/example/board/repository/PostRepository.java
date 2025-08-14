package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.domain.Post;

@Repository                                          //엔티티, 기본키 필드의 자료형 
public interface PostRepository extends JpaRepository<Post, Integer> {




}
