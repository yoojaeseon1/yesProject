Kakao.init('630e98d8425188c04dae0728c65822bb');

$(document).ready(function() {

	document.cookie = 'same-site-cookie=foo; SameSite=Lax';
	document.cookie = 'cross-site-cookie=bar; SameSite=None; Secure';

});

function loginWithKakao() {

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
					$.ajax({
						type : "POST",
						url : "/kakaologin",
						data : {
							"id" : id,
							"name" : name
						},
						success : function(data) {
							alert("내정보 수정 해주세요.");
							$(location).attr("href", "http://localhost:8080/");

						},
						error : function(request, status, error) {
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
	$('#login-changePW').css('display', 'none');
	$("#login-temporarilyPW").css('display', 'none');
	$('#loginForm').css('display', 'block');
/*	$('#')*/
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

			var name = $('.name').val();
			var birth = $('.birth').val();
			var email = $('.email').val();
			$.ajax({
				type : "POST",
				url : "/findID",
				data : {
					"name" : name,
					"birthDate" : birth,
					"email" : email
				},
				success : function(data) {
					if (data == "error") {
						alert("일치하는 아이디가 없습니다.");
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

			id = $('.idChangePW').val();
			var name = $('.nameChangePW').val();
			var birth = $('.birthChangePW').val();
			var email = $('.emailChangePW').val();
			var answer = $('.pwQuestionChangePW').val();

			$.ajax({
				url : "./findPW",
				type : "POST",
				data : {
					"id" : id,
					"name" : name,
					"birthDate" : birth,
					"email" : email,
					"pwQuestion" : answer
				},
				success : function(data) {

					if (data == "success") {
						alert("새로운 비밀번호를 설정해주세요.");
						$('#login-changePW').css('display', 'none');
						$('#login-changePW2').css('display', 'block');
					} else {
						alert("일치하는 정보가 없습니다.");
						
					}
				}
			});

		},
		success : function(element) {
		}
	});
	
	
	jQuery("#findPWFormTemporarily").validate({ // 임시비밀번호 발급
		rules : {
			id : {
				required : true
			},
			name : {
				required : true,
				minlength : 2
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			name : {
				required : "필수정보입니다"
			},
			email : {
				required : "필수정보입니다",
				email : "이메일 주소를 입력해주세요"
			}
		},
		errorPlacement : function(error, element) {
			if (element.is(".form-control")) {
				error.appendTo(element.parent().parent());
			} else {

			}
		},
		submitHandler : function() {

			id = $('.idTempPW').val();
			var name = $('.nameTempPW').val();
			var email = $('.emailTempPW').val();

			$.ajax({
				url : "./sendEmailTempPW",
				type : "POST",
				data : {
					"id" : id,
					"name" : name,
					"email" : email
				},
				success : function(data) {

					if (data == "success") {
						alert("입력하신 메일로 임시 비밀번호가 전송되었습니다. 최초 로그인 이후에 비밀번호를 변경해주세요.");
						$("#login-temporarilyPW").css('display', 'none');
						$('#loginForm').css('display', 'block');
					} else {
						alert("일치하는 정보가 없습니다.");
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
			if (data == 'success') {
				alert('비밀번호 변경 성공');
				$('#login-changePW2').css('display', 'none');
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
});

naverLogin.init();


window.addEventListener('load', function() {
	naverLogin.getLoginStatus(function(status) {

		if (status) {

			var id = naverLogin.user.getId();
			var name = naverLogin.user.getName();
			var email = naverLogin.user.getEmail();

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


		}
	});
});

$('#findID').click(function(){
	$('#loginForm').css('display','none');
	$(".joinTitle").html("아이디 찾기");
	$('#findIDForm')[0].reset();
	$('#login-findID').css('display','inline-block');
});



$('#findPW').click(function(){ // 임시비번 / 변경 선택 창
	$('#loginForm').css('display','none');
	$(".joinTitle").html("비밀번호 찾기");
	$(".login-findPW").css('display', "inline-block");
/*	$('#findPWForm')[0].reset();
	$('#login-changePW').css('display','inline-block');
	$('#login-changePW').scrollTop(0);*/
});


$("#temporarilyPW").click(function(){
	$("#login-findPW").css('display', "none");
	$("#login-temporarilyPW").css('display', 'inline-block');
});

$("#changePW").click(function(){ // 비밀번호 변경
	$("#login-findPW").css('display', "none");
	$('#findPWForm')[0].reset();
	$('#login-changePW').css('display','inline-block');
	$('#login-changePW').scrollTop(0)
});





function logout() {
	

	$.ajax({
		type : "GET",
		url : "/logout",
		success : function(data) {			
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
