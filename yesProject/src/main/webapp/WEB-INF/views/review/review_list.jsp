<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!Doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<!-- <link
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
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>  -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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

@media ( min-width : 1095px) { /*사이즈 클 때*/
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
	#menu a {
		font-family: 'Nanum Gothic', sans-serif;
	}
	#image {
		height: 100px;
		width: 150px;
	}

	/*.drop_back{
                    background-color: #e04f5f; 
                    height: 120px;
                }*/
}

@media ( max-width : 1110px) { /*사이즈 작을 때*/
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
	#menu a {
		font-family: 'Nanum Gothic', sans-serif;
	}
	#image {
		height: 100px;
		width: 150px;
	}
	/*축소화시 메뉴 수정*/
	/*
                .navbar-collapse{
                    width: 200px;
                    padding-right: 0px;
                }
                .navbar-nav{
                    background-color: black;    
                }
*/
}
</style>
</head>
<body style="overflow-y: auto;">
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<br />
	<form class="form-inline" role="form" method="GET"
		action="review_list">
		검색분류
		<!--  new paging -->
		<div class='box-body'>
		<select name="searchType">
			<option value="n"
				<c:out value="${cri.searchType == null ? 'selected' : ' '}"/>>
				---</option>
			<option value="t"
				<c:out value="${cri.searchType == 't' ? 'selected' : ' ' }"/>>
				제목</option>
			<option value="c"
				<c:out value="${cri.searchType == 'c' ? 'selected' : ' ' }"/>>
				내용</option>
			<option value="w"
				<c:out value="${cri.searchType == 'w' ? 'selected' : ' ' }"/>>
				작성자</option>
			<option value="tc"
				<c:out value="${cri.searchType == 'tc' ? 'selected' : ' ' }"/>>
				제목+내용</option>
			<option value="cw"
				<c:out value="${cri.searchType == 'cw' ? 'selected' : ' ' }"/>>
				내용+작성자</option>
			<option value="tcw"
				<c:out value="${cri.searchType == 'tcw' ? 'selected' : ' ' }"/>>
				제목+내용+작성자</option>
		</select>
		</div>
		
		 <br /> <br /> <br /> 검색
		<div class="form-group">
			<label class="sr-only" for="exampleInputEmail2">키워드검색 </label> <input
				type="text" class="form-control" id="exampleInputEmail2"
				name="keyword" id="keywordInput" value="${cri.keyword }">
		</div>
		<button type="submit" class="btn btn-default">검색</button>
	</form>

	<br />
	<br />
	<br />
	<table class="table table-hover" id="table">
		<%--	<c:forEach items="${alist }" var="bean">
    			<tr>
    				<td>${bean.branchID }</td>
    				<td>${bean.clientID }</td>
    				<td>${bean.index }</td>
    				<td>${bean.title }</td>
    			</tr> 
    		</c:forEach> --%>
		<thead>
			<tr>
				<th class="text-center" style="cursor: pointer;">글번호</th>
				<th class="text-center" style="cursor: pointer;">대표이미지</th>
				<th class="text-center" style="cursor: pointer;">제목</th>
				<th class="text-center" style="cursor: pointer;">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="bean" items="${reviews}" varStatus="status">
				<tr>
					<td class="text-center" style="cursor: pointer;"
						onClick=" location.href='review_list/readReviewPage${pageMaker.makeSearch(pageMaker.cri.page)}&reviewIndex=${bean.reviewIndex } ">${bean.reviewIndex}</td>
					<td class="text-center" style="cursor: pointer;"
						onClick=" location.href='review_list/readReviewPage${pageMaker.makeSearch(pageMaker.cri.page)}&reviewIndex=${bean.reviewIndex }' "><img
						src="${pageContext.request.contextPath}/resources/review_imgs/${images[status.index].imageName }"
						id="image" /></td>
					<td class="text-center" style="cursor: pointer;"
						onClick=" location.href='review_list/readReviewPage${pageMaker.makeSearch(pageMaker.cri.page)}&reviewIndex=${bean.reviewIndex }' ">${bean.title}</td>
					<td class="text-center" style="cursor: pointer;"
						onClick=" location.href='review_list/readReviewPage${pageMaker.makeSearch(pageMaker.cri.page)}&reviewIndex=${bean.reviewIndex }' ">
						<%-- <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${bean.registeredDate }"/>--%>
						${bean.registeredDate }
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
		<ul class="pagination">
			<c:if test="${pageMaker.prev }">
				<li><a href="${pageMaker.makeSearch(pageMaker.startPage-1) }">&laquo;</a></li>
			</c:if>
			<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="currentPageIndex" >
				<li <c:out value="${pageMaker.cri.page == currentPageIndex  ? 'class=active' : ' '}"/>>
					<a href="${pageMaker.makeSearch(currentPageIndex) }">${currentPageIndex }</a>
				</li>
			</c:forEach>
			<c:if test="${pageMaker.next }">
				<li><a href="${pageMaker.makeSearch(pageMaker.endPage+1) }">&raquo;</a>	</li>						
			</c:if>
		</ul>



	<%-- 	<br />
	<c:if test="${member != null }">
	<a class="btn btn-default" href="./review_write" role="button">글쓰기</a>
	</c:if>
	<br /> --%>

	<script>
	
		$(document).ready(
			function(){
				
				$('#searchBtn').on(
					"click"	
					, function(event) {
						self.location="review_list"
						+ '${pageMaker.makeQuery(1)}'
						+ "&searchType="
						+ $("select option:selected").val()
						+ "&keyword=" + encodeURIComponent($('#keywordInput').val());
					}
				);
				
				$("#newBtn")
				
			}	
		);
		
	
		function goPage(pages, lines) {
			location.href = '?' + "pages=" + pages;
		}
	</script>
</body>
</html>