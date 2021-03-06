<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> -->
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
		font-size: 2s0px;
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

	/*추가한 코드*/
	#frame {
		width: 800px;
		margin-left: auto;
		margin-right: auto;
	}
	#content {
		height: 300px;
		width: 500px;
	}
	#detail_imgs {
		height: 400px;
		width: 250px;
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
	#detail_imgs {
		height: 250px;
		width: 250px;
	}
}
</style>

</head>

<script type="text/javascript">
	function clickLike() {

		$.ajax({
			type : 'POST',
			url : "<c:url value='clickLike'/>",
			data : $("#likeClickForm").serialize(),
			success : function(data) {
				if (data == "success") {
					getLikeCount();
				} else
					alert("로그인 해주세요.");
			},
			error : function(request, status, error) {
			}
		});
	}

	/**
	 * 초기 페이지 로딩시 댓글 불러오기
	 */
	$(function() {
		getLikeCount();

	});

	/**
	 * 댓글 불러오기(Ajax)
	 */
	function getLikeCount() {

		$.ajax({
					type : 'GET',
					url : "<c:url value='reviewLike'/>",
					dataType : "json",
					data : $("#likeCntForm").serialize(),
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success : function(data) {

						var likeCntHtml = "";
						var likeClickHtml = "";

						if (data.checked) {
							likeCntHtml += "<h3>" + data.likeCount + "</h3>";
							likeCntHtml += "<input type='hidden' id='reviewIndex' name='reviewIndex' value='${bean.reviewIndex}'>";
							likeClickHtml += "<a onClick='clickLike()' class='btn pull-right btn-success'>좋아요 취소</a>";
							likeClickHtml += "<input type='hidden' id='reviewIndex' name='reviewIndex' value='${bean.reviewIndex}' />";
							likeClickHtml += "<input type='hidden' id='checked' name='checked' value='false' />";
							likeClickHtml += "<input type='hidden' id='clientID' name='clientID' value='${member.id}' />"; // controller에서 session으로 확인하니까 필요없다.
						} else {
							likeCntHtml += "<h3>" + data.likeCount + "</h3>";
							likeCntHtml += "<input type='hidden' id='reviewIndex' name='reviewIndex' value='${bean.reviewIndex}'>";
							likeClickHtml += "<a onClick='clickLike()' class='btn pull-right btn-success'>좋아요</a>";
							likeClickHtml += "<input type='hidden' id='reviewIndex' name='reviewIndex' value='${bean.reviewIndex}' />";
							likeClickHtml += "<input type='hidden' id='checked' name='checked' value='true' />";
							likeClickHtml += "<input type='hidden' id='clientID' name='clientID' value='${member.id}' />"; // session으로 value 변경해야됨
						}

						$("#likeCnt").html(likeCntHtml);
						$("#likeClick").html(likeClickHtml);
					},
					error : function(request, status, error) {

					}
				});
	}

	function deleteReview() {
		
		if(confirm("정말 삭제하시겠습니까?") == true) {

		$.ajax({
			url : "./reviewDelete",
			type : "DELETE",
			data : {reviewIndex : ${bean.reviewIndex}},
			success : function(data) {
				if (data == "success") {
					alert("정상적으로 삭제되었습니다.");
					location.href = "/reviewList?page=${cri.page}&perPageNum=${cri.perPageNum}&searchType=${cri.perPageNum}&keyword=${cri.keyword}";
				} else if (data == "no login")
					alert("로그인 해주세요.");
				else
					alert("고객님이 등록한 리뷰가 아닙니다.");
			}
		});
	}
		else
			return;
	} 


	function editReview() {


		var reviewWritingID = "${bean.clientID}";
		var loginedID;

		$.ajax({

			url : "../checkLogined",
			type : "GET",
			data : {
				clientID : "${bean.clientID}"
			},
			success : function(data) {
				if (data == "1") {
					location.href = "../reviewEdit?page=" + page
					+"&perPageNum=" + ${cri.perPageNum}
					+"&searchType=" + ${cri.searchType} + "&keyword="
							+ "${cri.keyword}&reviewIndex=" + ${bean.reviewIndex};
				} else if (data == "2") {
					alert("고객님이 등록한 리뷰가 아닙니다.");
				} else
					alert("로그인 해주세요.");
			}
		});


	}
</script>
<body style="overflow-y: auto;">
	<jsp:include page="../layout/header.jsp" />
	<div class="container"
		style="border-bottom: 1px solid #CCCCCC; padding-left: 0px; padding-right: 0px; border-bottom-color: #e04f5f; margin-top: 30px;">

		<div class="col-sm-9"
			style="width: 100%; padding-left: 14px; padding-right: 14px;">
			<h1 style="padding: 5px; margin-bottom: 20px;">
				<a href="#" style="color: black;">리뷰 보기</a>
			</h1>

		</div>
	</div>
	<form method="POST" id="deleteReviewForm" style="text-align: right">
		<a class="btn btn-default"
			href="/reviewList?page=${cri.page}&perPageNum=${cri.perPageNum}&searchType=${cri.searchType}&keyword=${cri.keyword}"
			role="button">뒤로</a>
		<button type="button" class="btn btn-default" onClick="deleteReview()">삭제</button>
		<button type="button" class="btn btn-default" onClick="editReview()">수정</button>
		<input type="hidden" name="reviewIndex" value="${bean.reviewIndex }" />
		<input type="hidden" name="page" value="${cri.page }" /> <input
			type="hidden" name="perPageNum" value="${cri.perPageNum }" /> <input
			type="hidden" name="searchType" value="${cri.searchType }" /> <input
			type="hidden" name="keybord" value="${cri.keyword }">
	</form>

	<table class="table" id="frame">
		<tr>
			<th>글번호</th>
			<td>${bean.reviewIndex }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${bean.title }</td>
		</tr>
		<tr>
			<th>평점</th>
			<td>${bean.rating}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${bean.registeredDate}</td>
		</tr>
		<tr>
			<th>좋아요</th>
			<td>
				<form id="likeCntForm" name="likeCntForm" method="post">
					<div id="likeCnt">
						<input type='hidden' id='reviewIndex' name='reviewIndex'
							value='${bean.reviewIndex}'>
					</div>
				</form>
				<form id="likeClickForm" name="likeClickForm" method="post">
					<div id="likeClick"></div>
				</form>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<div class="panel panel-default" id="content">
					<div class="panel-body" id="content">${bean.content }</div>
				</div>
			</td>
		</tr>
	</table>
	<c:if test="${mainImage.imageName != null}">
		<div id="myCarousel" class="carousel slide" data-ride="carousel"
			id="frame">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<c:forEach var="imageCount" begin="1" end="${ numImages - 1}">
					<li data-target="#myCarousel" data-slide-to="${imageCount }"></li>
				</c:forEach>
			</ol>
			<div class="carousel-inner" id="frame">
				<c:if test="${mainImage.imageName != null }">
					<div class="item active">
						<img
							src="${pageContext.request.contextPath}/resources/review_imgs/${mainImage.imageName }"
							style="width: 100%" alt="First slide" id="detail_imgs">
						<div class="container">
							<div class="carousel-caption"></div>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty subImages}">
					<c:forEach items="${subImages }" var="subImage">
						<div class="item">
							<img
								src="${pageContext.request.contextPath}/resources/review_imgs/${subImage.imageName }"
								style="width: 100%" data-src="" alt="Second slide"
								id="detail_imgs">
							<div class="container">
								<div class="carousel-caption"></div>
							</div>
						</div>
					</c:forEach>
				</c:if>
			</div>
			<!--이전, 다음 버튼-->
			<a class="left carousel-control" href="#myCarousel" data-slide="prev"><span
				class="glyphicon glyphicon-chevron-left"></span></a> <a
				class="right carousel-control" href="#myCarousel" data-slide="next"><span
				class="glyphicon glyphicon-chevron-right"></span></a>
		</div>
	</c:if>



	<%@ include file="reviewComment.jsp"%>
</body>
</html>