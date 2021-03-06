<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>         
<!Doctype html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<style>
	
	.table-label{
		font-size:20px;
		text-align:center;
		background-color:RGB(255,212,212);
		height:100px;
		padding-top:20px;
		
	}
	
	.table input{
	
		width:100%;
		height:50px;
	}
	
	 input.error

			{
			        border: 1px solid red
			}

	 p.error
			
			{
			        display:block;
			        color:red;
			} 
	#ex1{
	
	width:500px;
	height:500px;
	overflow:visible;
	display:none;
	}
	#deletebtn{
	
	width:400px;
	height:100px;
	overflow:visible;
	}
	#deletebtn a{
	 float:right;
	}
	
	.table a{
	
	color:black;
	display:inline-block;
	text-decoration: none;
	}
	
	</style>
	
	<script>
	 
	function deleteReview(reviewIndex){
		 
		 $('#deleteReview').click(function(){
			 $.ajax({
					url:'./deleteReview',
					method:'POST',
					data:{'reviewIndex':reviewIndex},
					success:function(data){
						<!--location.href='/myReviewWrite?page='+${currentPageIndex}; -->
						location.href='/myReviewWrite?page=' + ${pageMaker.cri.page};
					}
					}); 
		 });
	 } 
	 
	 
	
	</script>
	

    </head>
    <body style="overflow:auto; z-index:0; positio:relative;">
       <jsp:include page="../layout/header.jsp"></jsp:include>
        <div class="container" style=" border-bottom: 1px solid #CCCCCC;">
           
           <div class="container" style=" border-bottom: 1px solid #CCCCCC; padding-left:0px; padding-right:0px; 
               border-bottom-color: #e04f5f; margin-top:30px;">
                
              <div class="col-sm-9" style="width: 100%; padding-left: 14px; padding-right:14px;  ">
                  <h1 style="padding: 5px; margin-bottom: 20px; ">
                      <a href="" style="color: black;">작성글 보기</a>
                  </h1>

              </div>
            </div>
            
            <!-- modal -->
            
            
	        <div id="ex1" class="modal modal3">
	            <div class="detailModalTop" style="width:500px;">
    				<div id="branchName" class="joinTitle">
    				매장이름
    				</div>
	            </div> 
	            <div style="height:400px; text-align:center;margin-top:100px;">
				<table style="width: 60%;margin: 0px auto;height: 100%; margin-top:20px;">
				<tr style="height:10%;">
				<td><img src="./imgs/detailImgs/placeholder.png"/></td>
				<td id="branchAddr"></td>
				</tr>
				<tr style="height:10%;">
				<td><img src="./imgs/detailImgs/signal.png"/></td>
				<td id="branchAddr2"></td>
				</tr>
				<tr style="height:10%;">
				<td><img src="./imgs/detailImgs/classic-phone.png"/></td>
				<td id="branchPhone"></td>
				</tr>
				<tr style="height:10%;">
				<td><img src="./imgs/detailImgs/calendar.png"/></td>
				<td id="branchDate"></td>
				</tr >
				<tr style="height:10%;">
				<td><img src="./imgs/detailImgs/time.png"/></td>
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
                    	<th>글번호</th>
                        <th >매장 이름</th>
                        <th style="width:30%;">제목</th>
                        <th>작성시간</th>
                        <th>평점</th>
                    </tr>
                    </thead>
                    <tbody>
                 
						<c:forEach items="${myReviews}" var="review" varStatus="status">
                    	<tr>
                    	<td>${review.reviewIndex }</td>
                    	<td>${review.branchID }</td>
                    	<%-- <td><a id="modal"href="#ex1" rel="modal:open" >${review.title }</a></td> --%>
                    	<td><a href="/reviewList/${review.reviewIndex }" >${review.title }</a></td>
                    	<td>${review.registeredDate }</td>
                    	<td>${review.rating }
                    	<a id="deleteModal" href="#deletebtn2" rel="modal:open" style="margin-left:20px; font-size: 12px; color:red" onclick="javascript:deleteReview('${review.reviewIndex}');"   >삭제</a>
                    	</td>
                    	</tr>
						</c:forEach>
                    

                    </tbody>

                   </table>
						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li><a
									href="${pageMaker.makeSearch(pageMaker.startPage-1) }">&laquo;</a></li>
							</c:if>
							<c:forEach begin="${pageMaker.startPage }"
								end="${pageMaker.endPage }" var="currentPageIndex">
								<li
									<c:out value="${pageMaker.cri.page == currentPageIndex  ? 'class=active' : ' '}"/>>
									<a href="${pageMaker.makeSearch(currentPageIndex) }">${currentPageIndex }</a>
								</li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li><a href="${pageMaker.makeSearch(pageMaker.endPage+1) }">&raquo;</a>
								</li>
							</c:if>
						</ul>

						<div id="deletebtn2" class="modal2" style="display:none;height:150px;">
                   	 <p>예약을 취소하시겠습니까?</p>
                   	 <div style="width:150px; margin:0px auto;">
                   	  <a href="#" class="btn btn-default" id="deleteReview" >예</a>
			          <a href="#" class="btn btn-default" rel="modal:close">아니오</a>
                   	 </div>
                   	 </div>
                   
                   
                   </article>
                   </section>
                </div>

            </div>
            </div>      
                
       
    </body>
</html>