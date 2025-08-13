const btnLogin = document.querySelector('#btn-login');

btnLogin.addEventListener('click', (e) => {
	e.preventDefault();
	
	const user = {
		username: document.querySelector('#username').value,
		password: document.querySelector('#password').value
	}
	
	fetch('/auth/login', {
		method: 'POST',
		headers : {
			'Content-Type' : 'application/json; charset=utf-8'
		},
		body: JSON.stringify(user)
		}) .then(response => response.json())
	.then(json => {
		alert(json.data);
		
		if(json.status == 200) 
			window.location.href = "/";
		
		
	}).catch(error => {
		console.error("로그인 요청 실패" + error)
	})
	})

