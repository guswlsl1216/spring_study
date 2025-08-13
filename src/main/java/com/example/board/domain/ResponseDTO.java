package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //롬북
@NoArgsConstructor //각각의 생성자
@AllArgsConstructor
public class ResponseDTO<T> {      //스트링, 배열로 받을 수도 있으니까 제너릭 클래스로 생성

	private int status; // 상태 코드
	private T data;  // 응답 데이터         // 결과 : 성공했습니다 (String) , 목록 : 리스트


}
// 이거를 생성해서 보내줄거임 
