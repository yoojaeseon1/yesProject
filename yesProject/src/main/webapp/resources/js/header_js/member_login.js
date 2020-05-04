Kakao.init('630e98d8425188c04dae0728c65822bb');

$(document).ready(function() {

	document.cookie = 'same-site-cookie=foo; SameSite=Lax';
	document.cookie = 'cross-site-cookie=bar; SameSite=None; Secure';

});

function loginWithKakao() {
	// 로그인 창을 띄웁니다.

	console.log("loginWithKakao");

	Kakao.Auth.login({
		throughTalk : false,
		persistAccessToken : false,
		success : function(authObj) {
			Kakao.API.request({
				url : '/v2/user/me',
				success : function(res) {
					console.log(JSON.stringify(res.kaccount_email));
					var id = res.id;
					var name = JSON.stringify(res.properties.nickname);
					console.log("before ajax ");
					console.log("id : ", id);
					console.log("name : ", name);
					$.ajax({
						type : "POST",
						url : "/kakaologin",
						data : {
							"id" : id,
							"name" : name
						},
						success : function(data) {
							alert(data);
							$(location).attr("href", "http://localhost:8080/");

						},
						error : function(request, status, error) {
							console.log("fail ajax");
							alert("code:" + request.status + "\n" + "message:"
									+ request.responseText + "\n" + "error:"
									+ error);
						}
					});
				},
				fail : function(err) {
					console.log("fail login");
					alert(JSON.stringify(err));
				}
			})
		}
	});
}

function loginJoin() {
	$('#loginForm').css('display', 'none');
	$(".joinTitle").html("회원가입");
	$('.step1').clone(true).appendTo('#login');
	$('.step2').clone(true).appendTo('#login');
}

function loginCheck() {
	var close = $(".close-modal").html();
	console.log("close : ", close);
	var id = $('.id').val();
	var pw = $('.password').val();
	$.ajax({
		type : "POST",
		url : "/login",
		data : {
			"id" : id,
			"password" : pw
		},
		success : function(data) {
			if (data == 'success') {
				alert("로그인 되었습니다.");
				location.href = location.href;
			} else {
				alert("아이디 또는 비밀번호를 확인해주세요.");
			}
		},
		error : function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:"
					+ request.responseText + "\n" + "error:" + error);
		}
	});
}

function loginBack() {
	$(".joinTitle").html("로그인");
	$('#login-findID').css('display', 'none');
	$('#login-findPW').css('display', 'none');
	$('#loginForm').css('display', 'block');
}

$('#findID').click(function() {

	jQuery("#findIDForm").validate({
		rules : {
			name : {
				required : true
			},
			birthDate : {
				required : true
			},
			email : {
				required : true,
				email : true
			},
		},
		messages : {
			name : {
				required : "필수정보입니다",
			},
			birthDate : {
				required : "필수정보입니다"
			},
			email : {
				required : "필수정보입니다",
				email : "이메일 주소를 입력해주세요"
			},
		},
		errorPlacement : function(error, element) {
			if (element.is(".form-control")) {
				error.appendTo(element.parent().parent());
			} else {

			}
		},
		submitHandler : function() {
			console.log("validate submitted");

			var name = $('.name').val();
			var birth = $('.birth').val();
			var email = $('.email').val();
			// $.css({cursor:"wait"});
			$.ajax({
				type : "POST",
				url : "/findID",
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
		},
		success : function(element) {
			console.log("validate success");
		}
	});

});

var id;

$('#findPW').click(function() {

	jQuery("#findPWForm").validate({
		rules : {
			id : {
				required : true
			},
			name : {
				required : true,
				minlength : 2
			},
			birthDate : {
				required : true
			},
			email : {
				required : true,
				email : true
			},
			pwQuestion : {
				required : true
			}
		},
		messages : {
			name : {
				required : "필수정보입니다"
			},
			birthDate : {
				required : "필수정보입니다"
			},
			email : {
				required : "필수정보입니다",
				email : "이메일 주소를 입력해주세요"
			},
			pwQuestion : {
				required : "필수정보입니다"
			}
		},
		errorPlacement : function(error, element) {
			if (element.is(".form-control")) {
				error.appendTo(element.parent().parent());
			} else {

			}
		},
		submitHandler : function() {
			// $.css({cursor:"wait"});

			id = $('.id2').val();
			var name = $('.name2').val();
			var birth = $('.birth2').val();
			var email = $('.email2').val();
			var answer = $('.pwQuestion').val();

			console.log("finePW_btn id : ", id);

			$.ajax({
				url : "./findPW",
				type : "POST",
				data : {
					"id" : id,
					"name" : name,
					"birth" : birth,
					"email" : email,
					"answer" : answer
				},
				success : function(data) {

					if (data == "error") {
						alert("일치하는 정보가 없습니다.");
					} else {
						alert("새로운 비밀번호를 설정해주세요.");
						$('#login-findPW').css('display', 'none');
						$('#login-findPW2').css('display', 'block');
					}
				}
			});

		},
		success : function(element) {
		}
	});
});

$('#updatePW').click(function() {
	var pw = $('.pw').val();
	var confirm = $(".confirm").val();

	console.log("id : ", id);
	console.log("pw : ", pw);
	console.log("confirm : ", confirm);

	if (pw != confirm) {
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}

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
	callbackUrl : "http://localhost:8080",
	loginButton : {
		color : "green",
		type : 3,
		height : 47
	},
	isPopup : false,
	callbackHandle : true
// callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다.
});

// (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출
naverLogin.init();

// var email = naverLogin.user.getEmail();

// (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup
// close)

// 여기 까지는 naverLogin.user가 undefined인 상태다.
window.addEventListener('load', function() {
	naverLogin.getLoginStatus(function(status) { // 여기서 id, email 등의 status가

		console.log("login status : ", status);

		if (status) {

			var id = naverLogin.user.getId();
			var name = naverLogin.user.getName();
			var email = naverLogin.user.getEmail();
			console.log("id : ", id);
			console.log("name : ", name);
			console.log("email : ", email);

			$.ajax({
				type : "POST",
				url : "/naverLogin",
				data : {
					"id" : id,
					"name" : name,
					"email" : email
				},
				success : function(data) {
					if (data == "success") {
					}
				}
			});

			return;


		} else {

			console.log("logout");
		}
	});
});

function logout() {
	

	$.ajax({
		type : "GET",
		url : "/logout",
		success : function(data) {			
			console.log("logout success");
			naverLogin.getLoginStatus(function(status) {
				console.log("naver login check : ", state);
			});
			
			naverLogin.logout();
			
			alert('로그아웃 되었습니다.');
			
			
			window.open('http://developers.kakao.com/logout', 'kakao_iframe',
					'width=2px, height=2px');

			
			if($(location).attr("href").substring(0,35) == "http://localhost:8080/#access_token") 
				location.href = "http://localhost:8080";
			else
				location.href = location.href;
		}
	});

	
}
