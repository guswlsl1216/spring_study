// 이렇게도 할 수 있다! 구경하려고! 오브젝트 하나 만들어서
const userObject = {
	
	init: function() {
		const btnSave = document.getElementById('btn-save');
		const btnModify = document.getElementById('btn-modify');
		const btnDelete = document.getElementById('btn-delete');
		
		if(btnSave) {
			btnSave.addEventListener( 'click', (e) => {
				e.preventDefault(); // 새로 고침되니까 막아준거임 
				
				this.insertUser(); // 오브젝트에 함수 쓴거임..! this로 호출 가능
			})		
		}
		
		if(btnModify){
			btnModify.addEventListener('click', (e) => {
				e.preventDefault();
				this.updateUser(); // 호출해주면 클릭했을 때 저 함수가 실해오디게 끔 호출! 
			})
		}
		
		if(btnDelete) {
			btnDelete.addEventListener('click', (e)=> {
				e.preventDefault();
				this.deleteUser();
			})
			
			
		}
		
		
		
		
	}, // 객체 생성한거니까 , 찍고 새로운 거 아래에 작성
	
	insertUser : function() {
//		alert('회원가입 요청')
	 	const user = {
			username : document.getElementById("username").value,
			password : document.getElementById("password").value,
			email : document.getElementById("email").value
			
		}	
	
//		console.log(user);
		fetch('/auth/join', {
			method : 'POST',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			body : JSON.stringify(user)     // 위에 users는 오브젝트기 때문에 json으로 변환해서 보내줘야 함! 
		}).then(response => response.json())	
		.then(json => {
//			console.log(json);
			alert(json.data);
			
			if(json.status == 400)
				return;
			
			window.location.href = "/";
		}).catch(error => {
			console.error('회원가입 중 오류 발생', error);
		})
	},
	
	updateUser: function() {
		const user = {
			id: document.getElementById('id').value,	
			username: document.getElementById('username').value,
			password: document.getElementById('password').value,
			email: document.getElementById('email').value	
	     } 
	 
	 fetch('/auth/info', {
		method : 'POST',
		body : JSON.stringify(user),
		headers : {
			'Content-Type' : 'application/json; charset=utf-8'
		}
	 }).then(response => response.json())
	 .then(result => {
		alert(result.data);
		
		window.location.href="/auth/info";
	 }).catch(error => {
		console.error("수정 요청 중 오류 발생", error);
	 })
	 
	},
	
	deleteUser: function() {
		const id = document.getElementById('id').value;
		
		fetch(`/auth/user?id=${id}`, {
			method: 'DELETE'
		}).then(response => response.json())
		.then(result => {
			alert(result.data);
			
			window.location.href = "/";
		}).catch(error => {
			console.error("탈퇴 처리 중 오류", error);
		})
	}
			
}

userObject.init();