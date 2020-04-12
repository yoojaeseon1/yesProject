Kakao.init('630e98d8425188c04dae0728c65822bb');

function loginWithKakao() {
	// 로그인 창을 띄웁니다.
	Kakao.Auth.login({
		throughTalk : false,
		persistAccessToken : false,
		success : function(authObj) {
			Kakao.API.request({
				url : '/v1/user/me',
				success : function(res) {
					console.log(JSON.stringify(res.kaccount_email));
					var id = res.id;
					var name = JSON.stringify(res.properties.nickname);
					$.ajax({
						type : "POST",
						url : "./kakaologin",
						data : {
							"id" : id,
							"name" : name
						},
						success : function(data) {
							alert(data);
							$(location).attr("href", "http://localhost:8080/");

							// $(location).attr("href",
							// "http://localhost:8090/yes/");
						},
						error : function(request, status, error) {
							alert("code:" + request.status + "\n" + "message:"
									+ request.responseText + "\n" + "error:"
									+ error);
						}
					});
				},
				fail : function(err) {
					alert(JSON.stringify(err));
				}
			})
		}
	});
}

function logoutKakao() {
	
	console.log("logout start");
	
	$.ajax({
		type : "GET",
		url : "/logout",
		success:function(data) {
			console.log("controller end");
			window.open('http://developers.kakao.com/logout', 'kakao_iframe',
			'width=2px, height=2px');
			console.log("logout");
			alert('로그아웃 되었습니다.');
			location.href = location.href;
		}
		
	});
	location.href = location.href;
	
	// $(location).attr("href", "http://localhost:8080/");

	// $(location).attr("href", "http://localhost:8090/yes/");
}

/*
 * Kakao.init('630e98d8425188c04dae0728c65822bb'); // 카카오 로그인 버튼을 생성합니다.
 * Kakao.Auth.createLoginButton({ container: '#kakao-login-btn', success:
 * function(authObj) { // 로그인 성공시, API를 호출합니다. Kakao.API.request({ url:
 * '/v2/user/me', success: function(res) {
 * console.log(JSON.stringify(res.properties.profile_image));
 * console.log(JSON.stringify(res.properties.nickname)); var
 * name=JSON.stringify(res.properties.nickname); $.ajax({ type:"POST",
 * url:"./kakaologin", data:{ "name":name }, success:function(data){ } });
 * $(location).attr("href","http://localhost:8090/yes/"); }, fail:
 * function(error) { alert(JSON.stringify(error)); } }); }, fail: function(err) {
 * alert(JSON.stringify(err)); } });
 */

function loginJoin() {
	$('#loginForm').css('display', 'none');
	$('.step1').clone(true).appendTo('#login');
	$('.step2').clone(true).appendTo('#login');
}

function loginCheck() {
	var id = $('.id').val();
	var pw = $('.password').val();
	$.ajax({
		type : "POST",
		url : "/check",
		data : {
			"id" : id,
			"password" : pw
		},
		success : function(data) {
			// alert("성공 : 로그인");
			// var result=data.slice(0,2);
			// alert("data : ", data);
			// console.log("data : ", data);
			if (data == 'success') {
				alert("로그인 되었습니다.");
				console.log("currnetPage : ", location.href);
				location.href = location.href;
			} else {
				alert("아이디 또는 비밀번호를 확인해주세요.");
				// alert("data : ", data);
			}
		},
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
}

function loginBack() {
	$('#login-findID').css('display', 'none');
	$('#login-findPW').css('display', 'none');
	$('#loginForm').css('display', 'block');
}
$('#findID_btn').click(function() {
	var name = $('.name').val();
	var birth = $('.birth').val();
	var email = $('.email').val();

	$.ajax({
		type : "POST",
		url : "./find",
		data : {
			"name" : name,
			"birth" : birth,
			"email" : email
		},
		success : function(data) {
			// var result=data.slice(0,2);
			if (data == "error") {
				alert("일치하는 아이디가 없습니다.");
				// alert(data.slice(3));
			} else {
				alert("찾으시는 아이디는 " + data + " 입니다.");
				$('#login-findID').css('display', 'none');
				$('#loginForm').css('display', 'block');
			}
		}
	});
});
var id;
$('#findPW_btn').click(function() {
	id = $('.id2').val();
	var name = $('.name2').val();
	var birth = $('.birth2').val();
	var email = $('.email2').val();
	var answer = $('.pwQuestion').val();
	$.ajax({
		type : "POST",
		url : "./find2",
		data : {
			"id" : id,
			"name" : name,
			"birth" : birth,
			"email" : email,
			"answer" : answer
		},
		success : function(data) {
			// var result=data.slice(0,2);

			if (result == "error") {
				alert("일치하는 정보가 없습니다.");
			} else {
				$('#login-findPW').css('display', 'none');
				$('#login-findPW2').css('display', 'block');
			}
		}
	});

});
$('#updatePW').click(function() {
	var pw = $('.pw').val();

	$.ajax({
		type : "POST",
		url : "./pwUpdate",
		data : {
			"id" : id,
			"password" : pw,
		},
		success : function(data) {
			console.log(data);
			if (data == 'success') {
				alert('비밀번호 변경 성공');
				$('#login-findPW2').css('display', 'none');
				$('#loginForm').css('display', 'block');
			} else {
				alert('비밀변호 변경 실패');
			}
		}
	});
});

// naver login


var naverLogin = new naver.LoginWithNaverId({
	clientId : "2d7hDqSzyOEeQOrhGnyg",
	callbackUrl : "http://localhost:8080/",
	loginButton : {
		color : "green",
		type : 3,
		height : 60
	},
	isPopup : false,
	callbackHandle : true
// callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. 
});

// (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 
naverLogin.init();

//var email = naverLogin.user.getEmail();




//(4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close)
 

// 여기 까지는 naverLogin.user가 undefined인 상태다.
window.addEventListener('load', function() {
	naverLogin.getLoginStatus(function(status) { // 여기서 id, email 등의 status가
		if (status) {
			
			var id = naverLogin.user.getId();
			var email = naverLogin.user.getEmail();
			console.log("alreay logined id : ", id);
			console.log("alreay logined email : ", email);
			
			
			return;
			 //(5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 
//			var email = naverLogin.user.getEmail();
//			console.log("email : ", email);											// 초기화된다.
//			if (email == undefined || email == null) {
//				alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
//				 //(5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 
//				naverLogin.reprompt();
//				return;
//			}

			


//			$(location).attr("href", "http://localhost:8080/");

		} else { // 로그인이 안 되어 있는 경우
			
//			$.ajax({
//				type : "POST",
//				url : "./naverLogin",
//				data : {
//					"id" : id,
//					"name" : name,
//					"email" : email
//				},
//				success : function(data) {
//					if (data == "success") {
//						alert("네이버 아이디 연동 로그인이 되었습니다.");
//					}
//				}
//			});
			
			
//			window.location.replace("http://localhost:8080/");
			
//			console.log("callback 처리에 실패하였습니다.");
		}
	});
});