$(document).ready(function() {
  $('#content').summernote({
	height: 400
	
  });
});
// 객체로 관리할거가 때문에 초기에 object 객체로 생성해주고, 뒤에서 괄호 닫고 호출해줘욤! 
const postObject = {
	
	init: function() {
	 	const btnInsert = document.getElementById('btn-insert');
		const btnUpdate = document.getElementById('btn-update');
		const btnDelete = document.getElementById('btn-delete');
		
		if(btnInsert) {						
			btnInsert.addEventListener( 'click', (e) => {
				e.preventDefault(); // 기본동작 막는거 (새로고침)
				this.insertPost(); // 버튼 클릭하면 이 함수가 실행되고 아래에 콘솔에 뜨겟져 
				
				
			})
			
		}
		
		if(btnUpdate) {
			btnUpdate.addEventListener('click', (e) => {
				e.preventDefault();
				
				if(confirm('정말로 수정하시겠습니까?'))
				this.updatePost();
			})
		}
		
		if(btnDelete) {
			btnDelete.addEventListener('click', (e) => {
				e.preventDefault();
				this.deletePost();
			})
			
		}
		
						
		
	},
	// 제목과 내용을 뽑아서 서버로 날려줄거임 
	insertPost : function(){
		
		const post = {
			title : document.getElementById("title").value,
			content : document.getElementById("content").value
			}
		
//			console.log(post);
			
			fetch('/post', {
				method : 'POST',
				headers : {
					'Content-Type' : 'application/json; charset=utf-8'
				},
				body: JSON.stringify(post)
			}).then(response => response.json())	
			.then(result => {
			 	alert(result.data);
				
				window.location.href="/";
			}).catch(error => {
				console.log(error);
			})
		},
		// 요청 날려주는 메서드입니다! 모두 모두 
	updatePost : function () {
	
		const post = {
			id : document.getElementById('id').value,
			title : document.getElementById('title').value,
			content : document.getElementById('content').value
			}
	
		fetch('/post', {   // 펫취가 요청한거고 => 서버에서 응답해주면 response(매개변수)로 옴 
			method : 'PUT',
			headers : {
				'Content-Type' : 'application/json; charset=utf8'
			},
			body : JSON.stringify(post)  //post 담을건데 그냥 담으면 안되니까 JSON으로 ! 
										// 모든 통신은 문자열로만 받을 수 있습니당 JSON이 그렇게 해주는거임~
		}).then(response => response.json())	// 응답이 들어오면 then이 실행되는거에여! // 객체 형채로 변환시켜주고
			.then(result => {
				alert(result.data);  // 몇번 게시글 수정 완료~ // 근데 굳이 상태코드 안띄워줘도 되니깐~
				
				window.location.href="/";
			}).catch(error => { 
				console.log("수정 요청 중 문제 발생", error);
			})
	},
	
	deletePost : function () {
		// 요소를 가지고 있는 태그를 가져오는거임, 선택자
		// 다 input이라서 .value를 뽑아낼 수 있던건데 
		// 얘난 value 없음, 태그 안에 text를 뽑아내야함   
		const id = document.querySelector('.id').innerText;  // 내용물 뽑기 
					
			fetch(`/post/${id}`, {
			method : 'DELETE',
			headers	: {
				'Content-Type' : 'application/json; charset=utf8'
			},
			}).then(response => response.json())  
				.then(result => {
					alert(result.data);
					
					window.location.href="/";
				}).catch(error => {
					console.log("삭제 발생 중 문제 발생 ", error);
				})	
			
			
			
			
			
	}
}
postObject.init();