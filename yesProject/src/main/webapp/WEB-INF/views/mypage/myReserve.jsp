<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
.table-label {
	font-size: 20px;
	text-align: center;
	background-color: RGB(255, 212, 212);
	height: 100px;
	padding-top: 20px;
}

.table input {
	width: 100%;
	height: 50px;
}

input.error {
	border: 1px solid red
}

p.error {
	display: block;
	color: red;
}

#ex1 {
	width: 500px;
	height: 500px;
	overflow: visible;
	display: none;
}

#deletebtn {
	width: 400px;
	height: 100px;
	overflow: visible;
}

#deletebtn a {
	float: right;
}

.table a {
	color: black;
	display: inline-block;
	text-decoration: none;
}
</style>

<script>
	
</script>


</head>
<body style="overflow: auto; z-index: 0; positio: relative;">
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<div class="container" style="border-bottom: 1px solid #CCCCCC;">

		<div class="container"
			style="border-bottom: 1px solid #CCCCCC; padding-left: 0px; padding-right: 0px; border-bottom-color: #e04f5f; margin-top: 30px;">

			<div class="col-sm-9"
				style="width: 100%; padding-left: 14px; padding-right: 14px;">
				<h1 style="padding: 5px; margin-bottom: 20px;">
					<a href="" style="color: black;">예약 현황</a>
				</h1>

			</div>

			<form class="form-inline" role="form" method="GET"
				action="myReservation">
				검색분류
				<div class="box-body">
					<select name="searchType" id="searchType">
						<option value="n"
							<c:out value="${cri.searchType == null ? 'selected' : ' ' }" />>
							---</option>
						<option value="r"
							<c:out value="${cri.searchType == 'r' ? 'selected' : ' ' }" />>
							음식점명</option>
						<option value="d"
							<c:out value="${cri.searchType == 'd' ? 'selected' : ' ' }" />>
							날짜</option>
					</select>
					<div id="dateSelected">
					</div>
				</div>
				<button type="submit" id="reservationSearch" class="btn btn-default">검색</button>

			</form>

		</div>

		<!-- modal -->


		<div id="ex1" class="modal modal3">
			<div class="detailModalTop" style="width: 500px;">
				<div id="branchName" class="joinTitle">매장이름</div>
			</div>
			<div style="height: 400px; text-align: center; margin-top: 100px;">
				<table
					style="width: 60%; margin: 0px auto; height: 100%; margin-top: 20px;">
					<tr style="height: 10%;">
						<td><img
							src="${pageContext.request.contextPath}/resources/imgs/detailImgs/placeholder.png" /></td>
						<td id="branchAddr"></td>
					</tr>
					<tr style="height: 10%;">
						<td><img src="/resources/imgs/detailImgs/signal.png" /></td>
						<td id="branchAddr2"></td>
					</tr>
					<tr style="height: 10%;">
						<td><img src="/resources/imgs/detailImgs/classic-phone.png" /></td>
						<td id="branchPhone"></td>
					</tr>
					<tr style="height: 10%;">
						<td><img src="/resources/imgs/detailImgs/calendar.png" /></td>
						<td id="branchDate"></td>
					</tr>
					<tr style="height: 10%;">
						<td><img src="/resources/imgs/detailImgs/time.png" /></td>
						<td id="branchTime"></td>
					</tr>
				</table>
			</div>
		</div>


		<div style="padding-left: 40px; padding-right: 40px;">

			<header style="padding-top: 15px"></header>
			<div>
				<section style="width: 100%; padding-bottom: 30px">
					<article>
						<table class="table">
							<thead>
								<tr>
									<th>예약번호</th>
									<th>가맹점 이름</th>
									<th>예약 시간</th>
									<th>인원</th>
									<th style="width: 30%;">요청 사항</th>
									<th style="width: 15%;">이용 상태</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${reservations}" var="reservation"
									varStatus="status">
									<script>
										array.push('${bean.reserveTime}');
									</script>
									<tr>
										<td>${reservation.reserveIndex }</td>
										<td><a id="modal" href="#ex1" rel="modal:open"
											onclick="javascript:detail('${reservation.branchID}','${reservation.request }');">${reservation.branchName }</a></td>
										<td class="myReserveTime '${status.index}'">${reservation.reserveTime }</td>
										<td>${reservation.numPerson }</td>
										<td>${reservation.request }</td>
										<td>
											<%-- ${bean.useState }--%> <c:choose>
												<c:when test="${reservation.useState eq 'Y' }">
													<a style="margin-left: 20px; font-size: 12px;"
														class="btn btn-default"
														href="./reviewWrite/${reservation.branchID }/${reservation.reserveIndex }">리뷰작성
														가능</a>
												</c:when>
												<c:when test="${reservation.useState eq 'N' }">
													<a id="modal" href="#deletebtn" rel="modal:open"
														style="margin-left: 20px; font-size: 12px; color: red"
														onclick="javascript:del('${reservation.reserveTime}','${status.count }');">방문
														전(클릭시 취소)</a>
												</c:when>
												<c:otherwise>
													<div style="margin-left: 20px; font-size: 12px;">리뷰
														작성완료</div>
												</c:otherwise>
											</c:choose> 
										</td>
									</tr>
								</c:forEach>
							</tbody>

						</table>

						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li><a
									href="${pageMaker.makeSearch(pageMaker.startPage - 1) }">&laquo;</a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }"
								end="${pageMaker.endPage }" var="currentPageIndex">
								<li
									<c:out value="${pageMaker.cri.page == currentPageIndex ? 'class=active': ' ' }"/>>
									<a href="${pageMaker.makeSearch(currentPageIndex) }">${currentPageIndex }</a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li><a href="${pageMaker.makeSearch(pageMaker.endpage+1) }">&raquo;</a></li>
							</c:if>
						</ul>

						<div id="deletebtn" class="modal2"
							style="display: none; height: 130px;">
							<p>예약을 취소하시겠습니까?</p>
							<a href="#" class="btn btn-default" rel="modal:close">아니오</a> <a
								href="#" class="btn btn-default" id="del">예</a>
						</div>


					</article>
				</section>
			</div>

		</div>
	</div>


	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var selectedType = $(
									"[name='searchType'] option:selected")
									.val();

							console.log("selectedType : ", selectedType);

							if (selectedType == 'd') {
								var dateSelected = "<input type='date' name='beginDate' id='beginDate' value='${cri.beginDate}' max='9999-12-31'  class='form-control' > &nbsp&nbsp~&nbsp&nbsp";
								dateSelected += "<input type='date' name='endDate' id='endDate' value='${cri.endDate}' max='9999-12-31' class='form-control' >";
								$("#dateSelected").html(dateSelected);
							} else
								$("#dateSelected")
										.html(
												"<input type='text' value='${cri.keyword}' class='form-control' name='keyword'>");
						});

		$(function() {

			$("[name='searchType']")
					.change(
							function() {
								var selectedType = $(
										"[name=searchType] option:selected")
										.val();
								console.log("option changed : ", selectedType);
								if (selectedType == 'd') {
									var dateSelected = "<input type='date' name='beginDate' id='beginDate' max='9999-12-31'  class='form-control' > &nbsp&nbsp~&nbsp&nbsp";
									dateSelected += "<input type='date' name='endDate' id='endDate' max='9999-12-31' class='form-control' >";
									$("#dateSelected").html(dateSelected);
								} else {
									$("#dateSelected")
											.html(
													"<input type='text' class='form-control' name='keyword'>");
								}
							});
		});

		$("#reservationSearch")
				.click(
						function() {
							console.log("검색 click");
							var beginDate = $("#beginDate").val().split("-");
							var endDate = $("#endDate").val().split("-");

							if (beginDate[0] > endDate[0]
									|| (beginDate[0] == endDate[0] && beginDate[1] > endDate[1])
									|| (beginDate[0] == endDate[0]
											&& beginDate[1] == endDate[1] && beginDate[2] > endDate[2])) {
								alert("더 오래된 날짜부터 입력해주세요.");
								return false;
							}

						});

		var array = [];
		function del(e, e2) {

			$('#del').click(function() {
				var int2 = e2 - 1;
				console.log(array[int2]);
				$.ajax({
					url : './delreserve',
					method : 'POST',
					data : {
						'time' : array[int2]
					},
					success : function(data) {
						location.href = '.' + data;
					}
				});
			});
		}

		function detail(e, e2) {
			$('#branchName').empty();
			$('#branchAddr').empty();
			$('#branchAddr2').empty();
			$('#branchPhone').empty();
			$('#branchDate').empty();
			$('#branchTime').empty();

			$.ajax({
				url : './member_branchInfo',
				method : 'POST',
				data : {
					'id' : e
				},
				dataType : 'JSON',
				success : function(data) {

					$('#branchName').append(data.branchName);
					$('#branchAddr').append(data.roadAddress);
					$('#branchAddr2').append(data.jibunAddress);
					$('#branchPhone').append(data.phoneNum);
					$('#branchDate').append(data.opDate);
					$('#branchTime').append(data.opTime);
				},
				error : function(request, status, error) {
					alert("code:" + request.status + "\n" + "message:"
							+ request.responseText + "\n" + "error:" + error);
				}

			});
		}
	</script>
</body>
</html>