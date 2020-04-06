<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="/css/bootstrap.css">
</head>
<body>

	<div class="container">
		<form id="commentForm" name="commentForm" method="post">
			<br>
			<br>
			<div>
				<div>
					<span><strong>Comments</strong></span>
					<!--  <span id="numComment"></span> -->
				</div>
				<div>
					<table class="table">
						<tr>
							<td><textarea style="width: 1100px" rows="3" cols="30"
									id="comment" name="comment" placeholder="댓글을 입력하세요"></textarea>
								<br>
								<div>
									<a href="#" onClick="addComment('${bean.reviewIndex}')"
										class="btn pull-right btn-success">등록</a>
								</div></td>
						</tr>
					</table>
				</div>
			</div>
			<input type="hidden" id="reviewIndex" name="reviewIndex"
				value="${bean.reviewIndex}" />
			<!--         <input type="hidden" id="idx" name="idx" value="16" /> -->
		</form>
	</div>

	<div class="container">
		<form id="commentListForm" name="commentListForm" method="post">
			<div id="commentList"></div>
		</form>
	</div>

	<script type="text/javascript">
		/*
		 * 댓글 등록하기(Ajax)
		 */
		function addComment(code) { // check : 0 = 추가, 1=수정

			$.ajax({
				type : 'POST',
				url : "<c:url value='addComment'/>",
				data : $("#commentForm").serialize(),
				success : function(data) {
					if (data == "1") {
						alert("댓글이 등록되었습니다.");
						getCommentList();
						$("#comment").val("");
					} else{
						alert("로그인 해주세요.");
						getCommentList();
						$("#comment").val("");
					}
				},
				error : function(request, status, error) {
					//alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}

			});

		}

		/**
		 * 초기 페이지 로딩시 댓글 불러오기
		 */
		$(function() {

			getCommentList();

		});

		function getCommentList() {

			$
					.ajax({
						type : 'GET',
						url : "<c:url value='commentList'/>",
						dataType : "json",
						data : $("#commentForm").serialize(),
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success : function(data) {

							var html = "";
							var numComment = data.length;

							if (data.length > 0) {

								for (i = 0; i < data.length; i++) {
									if (data[i].commentIndex != null) {
										html += "<form id='commentInfo"+data[i].comment_idx+"' name='commentInfo' method='post'>";
										html += "<div>";
										html += "<div><table class='table'><h6><strong>"
												+ data[i].clientID
												+ "</strong>&emsp;<a href='#' onClick='changeToTextArea("
												+ data[i].commentIndex
												+ ",\""
												+ data[i].comment
												+ "\")'>수정</a>&emsp;<a href='#' onClick='deleteComment("
												+ data[i].commentIndex
												+ ")'>삭제</a></h6>";
										html += "<input type='hidden' id='comment_idx' name='comment_idx' value="+data[i].commentIndex+" /> ";
										html += "<input type='hidden' id='review_idx' name='review_idx' value='${bean.reviewIndex}' /> ";
										html += "<div id='editComment"+data[i].commentIndex+"'>"
												+ data[i].comment
												+ "</div><tr><td></td></tr>";
										html += "</table></form></div>";
										html += "</div>";
									} else {
										html += "<form id='commentInfo"+data[i].commentIndex+"' name='commentInfo' method='post'>";
										html += "</form>";
									}
								}
							} else {

								html += "<div>";
								html += "<div><table class='table'><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
								html += "</table></div>";
								html += "</div>";

							}

							$("#numComment").html(numComment);
							$("#commentList").html(html);
						},
						error : function(request, status, error) {

						}
					});
		}

		function deleteComment(commentIndex) {

			console.log(commentIndex);

			$.ajax({
				type : "POST",
				url : "./deleteComment",
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : "text",
				data : JSON.stringify({
					commentIndex : commentIndex
				}),
				success : function(result) {

					if (result == "success")
						alert("댓글이 삭제되었습니다.");
					else
						alert("고객님이 등록한 댓글이 아닙니다.");

					getCommentList();
				}
			});
		}

		function changeToTextArea(commentIndex, comment) {

			// console.log("updateComment : ", commentIndex);
			// console.log("updateComment : ", comment);
			$
					.ajax({

						type : "GET",
						url : "../checkLogined",
						data : {
							clientID : "${bean.clientID}"
						},
						success : function(data) {
							if (data == "1") {
								var updateArea = "<textArea id='updatedComment' rows='5' cols='130'>"
										+ comment + "</textArea>";
								updateArea += "<button type='button' onclick='getCommentList()' class='btn pull-right btn-danger'>취소</button>"
								updateArea += "<button type='button' onclick='updateComment("
										+ commentIndex
										+ ")' class='btn pull-right btn-success'>수정</button>";

								$("#editComment" + commentIndex).html(
										updateArea);

							} else if (data == "2") {
								alert("고객님이 등록한 댓글이 아닙니다.");
							} else {
								alert("로그인 해주세요.");
							}
						}
					});
		}

		function updateComment(commentIndex) {

			// var updatedComment = $("#updatedComment").val();

			// console.log("updateComment : " , updatedComment);

			$.ajax({

				url : "./editComment",
				type : "POST",
				data : {
					commentIndex : commentIndex,
					comment : $("#updatedComment").val()
				},
				success : function(data) {

					if (data == "1") {
						alert("수정이 완료되었습니다.");
						getCommentList();
					}
				}
			});
		}
	</script>

</body>
</html>
