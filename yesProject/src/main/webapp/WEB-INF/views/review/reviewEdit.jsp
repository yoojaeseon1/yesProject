<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link
	href="https://fonts.googleapis.com/css?family=Do+Hyeon|Jua|Nanum+Gothic"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<style>
* {
	font-family: 'Nanum Gothic', sans-serif;
}

nav a {
	font-family: 'Jua', sans-serif;
}

.searchMenu {
	font-family: 'Do Hyeon', sans-serif;
	font-size: 30px;
}

@media ( min-width : 1095px) {
	.container-fluid {
		background-color: #e04f5f;
		height: 120px;
	}
	.navbar-collapse {
		padding-top: 40px;
		padding-left: 360px;
		font-size: 20px;
	}
	li>a {
		color: white;
		margin-right: 50px;
		font-size: 30px;
	}
	.menuBtnToggle {
		color: white;
		font-size: 30px;
	}
	.navbar-right>li>a {
		font-size: 20px;
		margin-right: 0px;
		color: white;
	}
	#bs-example-navbar-collapse-1 a {
		text-decoration: none;
		text-decoration-color: white;
		color: white;
	}
	#dropdown a {
		color: black;
		font-size: 16px;
	}
	.dropdown-toggle:hover {
		color: black;
	}
}

@media ( max-width : 1110px) {
	li>a {
		font-size: 20px;
	}
	.navbar-header {
		float: none;
	}
	.navbar-left, .navbar-right {
		float: none !important;
	}
	.navbar-toggle {
		display: block;
	}
	.navbar-collapse {
		border-top: 1px solid transparent;
		box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.1);
		margin-top: 70px;
		float: left;
	}
	.navbar-fixed-top {
		top: 0;
		border-width: 0 0 1px;
	}
	.navbar-collapse.collapse {
		display: none !important;
	}
	.navbar-nav {
		float: none !important;
		margin-top: 7.5px;
	}
	.navbar-nav>li {
		float: none;
	}
	.navbar-nav>li>a {
		padding-top: 10px;
		padding-bottom: 10px;
	}
	.collapse.in {
		display: block !important;
	}
	.container-fluid {
		background-color: #e04f5f;
		height: 120px;
	}
	.navbar-collapse a {
		text-decoration: none;
		color: black;
	}
	#dropdown a {
		font-size: 15px;
	}
	#bs-example-navbar-collapse-1 {
		width: 100%;
	}
	#bs-example-navbar-collapse-1 a {
		display: block;
		clear: both;
	}
	#bs-example-navbar-collapse-1>ul>li {
		display: block;
		clear: both;
	}
}
</style>
</head>
<script type="text/javascript">
	function checkedRating() {

		var rating = ${review.rating};

		if (rating == 1) {
			$("#inlineRadio1").attr("checked", true);
		} else if (rating == 2) {
			$("#inlineRadio2").attr("checked", true);
		} else if (rating == 3) {
			$("#inlineRadio3").attr("checked", true);
		} else if (rating == 4) {
			$("#inlineRadio4").attr("checked", true);
		} else if (rating == 5) {
			$("#inlineRadio5").attr("checked", true);
		}
	}

	$(function() {
		checkedRating();
	});
	
	function checkMainImage(){
		
		var mainImage = document.getElementById("mainImage").value;
		var subImages = document.getElementById("subImages").value;
		
		if(mainImage.length == 0 && subImages.length > 0) {
			alert("메인 이미지를 첨부해주세요.");
			return false;
		} else
			document.reviewEditForm.submit();
		
	}
	
	
	
	
</script>
<body style="overflow-y:auto;">

	<jsp:include page="../layout/header.jsp"/>
	
	<form method="POST" enctype="multipart/form-data" name="reviewEditForm">
		<table class="table">
		<br>
			<h1>&nbsp&nbsp&nbsp리뷰 수정</h1>
			<br>
			<tr>
				<th>제목</th>
				<td><input type="text" id="title" name="title"
					value="${review.title }" /></td>
			</tr>
			<tr>
				<th>평점</th>
				<td><label class="radio-inline"> <input type="radio"
						name="rating" id="inlineRadio1" value="1"> 1
				</label> <label class="radio-inline"> <input type="radio"
						name="rating" id="inlineRadio2" value="2"> 2
				</label> <label class="radio-inline"> <input type="radio"
						name="rating" id="inlineRadio3" value="3"> 3
				</label> <label class="radio-inline"> <input type="radio"
						name="rating" id="inlineRadio4" value="4"> 4
				</label> <label class="radio-inline"> <input type="radio"
						name="rating" id="inlineRadio5" value="5"> 5
				</label></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea class="form-control col-sm-5" rows="15"
						id="content" name="content">${review.content}</textarea></td>
			</tr>
			<tr>
				<th>메인 이미지</th>
				<td><input type="file" name="mainImage" id = "mainImage"/></td>
			</tr>
			<tr>
				<th>남은 이미지들</th>
				<td><input type="file" name="subImages" id = "subImages" multiple="multiple" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="reviewIndex" id="reviewIndex" value="${review.reviewIndex }" />

		<input type="hidden" id="branchID" name="branchID" value="${ review.branchID }" />
		<input type="hidden" id="clientID" name="clientID" value="${review.clientID }" />
		<button type="button" class="btn btn-default" onClick="checkMainImage()">완료</button>
		<a class="btn btn-default" href="/reviewList/readReviewPage" role="button">취소</a>
	</form>
</body>
</html>