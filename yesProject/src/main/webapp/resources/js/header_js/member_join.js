<!-- 회원가입 Modal -->
<!-- Modal -->
$(function(){
    //empty추가하기
    $('.quiz').click(function(e){

        var array=$('.quizChoice').text().trim().split("?");
        var qc=array[0].trim();
        $('.quizChoice').text(e.target.text);
        e.target.text=qc+"?";

    });

    $('#backJoin').click(function(){
        $('.step2').css('display','none');
        $('.step1').css('display','inline-block');

    });

	$('.choice a').click(function(e){


        if($('.okbtn').is(':checked')===false)
        {
        alert('동의해주세요');
		e.preventDefault();
        }
        else{
            if(e.target.textContent=='고객')
            {
            	
            	$('#joinForm2')[0].reset();
                $('.step1').css('display','none');
                $('.step2').css('display','inline-block');
                $('.step2').scrollTop(0);
                $('#registNum input').val('0');
				$('#registNum').css('display','none');

            }
            else if(e.target.textContent='가맹점')
            {
            	$('#joinForm2')[0].reset();
            	// $('#registNum input').val('');
                $('.step1').css('display','none');
                $('#registNum').css('display', 'inline-block');
                $('.step2').css('display','inline-block');
                $('.step2').scrollTop(0);
                
/*                $('.step2').css('display','block');
*/            }

            jQuery("#joinForm2").validate({
                rules:{
                    id:{required:true,
                        minlength:4,
                        remote: {
                                url:"/checkIDDup",
                                type:"POST",
                                data: {
                                    id: function() {
                                       return $("#id").val();
                                    }
                               }
                               }
                    },
                    password:{required:true,minlength:4},
                    confirm:{required:true,equalTo:"#password"},
                    name:{required:true,minlength:2},
                    nickName:{required:true,minlength:2},
                    birthDate:{required:true},
                    phoneNum:{required:true,
                      minlength:13,
                      maxlength:13},
                    email:{required:true,
                    email:true},
                    registNum:{required:true, number:true, minlength:10, maxlength:10},
                    pwQuestion:{required:true}
                },
                messages:{
                    id:{
                        required:"필수정보입니다",
                        minlength:"최소 4자 이상 입력하세요",
                        remote:"중복된 아이디입니다"
                    },
                    password:{
                        required:"필수정보입니다",
                        minlength:"최소 4자 이상 입력하세요"
                    },
                    confirm:{
                        required:"필수정보입니다",
                        equalTo:"비밀번호가 일치하지 않습니다"
                    },
                    name:{
                        required:"필수정보입니다",
                        minlength:"최소 2자 이상 입력하세요"
                    },
                    nickName:{
                        required:"필수정보입니다",
                        minlength:"최소 2자 이상 입력하세요"
                    },
                    birthDate:{
                        required:"필수정보입니다"
                    },
                    phoneNum:{
                        required:"필수정보입니다",
                        number:"올바른 값을 입력해주세요"
                        },
                    email:{
                        required:"필수정보입니다",
                        email:"이메일 주소를 입력해주세요"
                    },
                    reigstNum:{
                        required:"필수정보입니다",
                        number:"숫자만 입력가능합니다"
                    },
                    pwQuestion:{
                        required:"필수정보입니다"
                    }
                },
                errorPlacement:function(error,element){
                    if(element.is(".form-control"))
                        {
                        error.appendTo(element.parent().parent());
                        }
                    else{

                    }
                },
                submitHandler:function(){
                    $.css({cursor:"wait"});
                    $('#joinForm2').submit();
                },
                success:function(element){
                }
            });
        }
    });


	$('#findID').click(function(){
		$('#loginForm').css('display','none');
		$(".joinTitle").html("아이디 찾기");
		$('#findIDForm')[0].reset();
		$('#login-findID').css('display','inline-block');
	});
	
	
	
	$('#findPW').click(function(){
		$('#loginForm').css('display','none');
		$(".joinTitle").html("비밀번호 찾기");
		$('#findPWForm')[0].reset();
		$('#login-findPW').css('display','inline-block');
		$('#login-findPW').scrollTop(0);
	});

});
