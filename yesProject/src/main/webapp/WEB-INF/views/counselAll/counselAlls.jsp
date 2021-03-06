<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" />



        <style>
            *{
                font-family: 'Nanum Gothic', sans-serif;
                
            }
            nav a{
                font-family: 'Jua', sans-serif;
            }
            .searchMenu{
                font-family: 'Do Hyeon', sans-serif;
                font-size: 30px;
            }
            @media (min-width: 1095px) {
                .container-fluid{
                    background-color: #e04f5f; 
                    height: 120px;
                }
                .navbar-collapse{
                    padding-top: 40px; 
                    padding-left: 360px; 
                    font-size: 20px;
                }
                li > a{
                    color: black;
                    margin-right: 50px; 
                    font-size: 30px;
                }
                .menuBtnToggle{
                    color: white;
                    font-size: 30px;
                }
                .navbar-right>li>a{
                    font-size: 20px;
                    margin-right: 0px;
                    color: white;
                }
                #bs-example-navbar-collapse-1 a{
                    text-decoration: none;
                    text-decoration-color: white;
                    color: white;
                }
                #dropdown a{
                    color: black;
                    font-size: 16px;
                }
                .dropdown-toggle:hover{
                    color: black;
                }
            }
            @media (max-width: 1110px) {
                li > a{
                    font-size: 20px;
                }
              .navbar-header {
                  float: none;
              }
              .navbar-left,.navbar-right {
                  float: none !important;
              }
              .navbar-toggle {
                  display: block;
              }
              .navbar-collapse {
                  border-top: 1px solid transparent;
                  box-shadow: inset 0 1px 0 rgba(255,255,255,0.1);
                  margin-top: 70px;
                  float: left;
              }
              .navbar-fixed-top {
                  top: 0;
                  border-width: 0 0 1px;
              }
              .navbar-collapse.collapse {
                  display: none!important;
              }
              .navbar-nav {
                  float: none!important;
                  margin-top: 7.5px;
              }
              .navbar-nav>li {
                  float: none;
              }
              .navbar-nav>li>a {
                  padding-top: 10px;
                  padding-bottom: 10px;
              }
              .collapse.in{
                  display:block !important;
              }
                .container-fluid{
                    background-color: #e04f5f; 
                    height: 120px;
                }
                .navbar-collapse a{
                    text-decoration: none;
                    color: black;
                }
                #dropdown a{
                    font-size: 15px;
                }
                #bs-example-navbar-collapse-1{
                    width: 100%;
                }
                #bs-example-navbar-collapse-1 a{
                    display: block;
                    clear: both;
                }
                #bs-example-navbar-collapse-1>ul>li{
                    display: block;
                    clear: both;
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
            #cube:hover{
                background-color: #e04f5f;
                color: white;
          
            }
            #paginationUI a{
                margin-right: 0px;
                font-size: 14px;
                color: black;
            }
            @media all and (max-width: 768px) {
                .col-xs-6 {
                    width: 100%;
                }
            }
            .pagination>.active>a, .pagination>.active>a:hover, .pagination>.active>a:visited, .pagination>.active>a:link{
            background-color: #e04f5f;
            border-color: #e04f5f;
            color: white;
            }
            
            .ui-datepicker select.ui-datepicker-month {
                width: auto;
            }
        </style>
        <%
        pageContext.setAttribute("cr", "\r");
        pageContext.setAttribute("cn", "\n");
        pageContext.setAttribute("crcn", "\r\n");
        pageContext.setAttribute("sp", "&nbsp;");
        pageContext.setAttribute("br", "<br/>");
        %>
    </head>
    <body>
        <jsp:include page="../layout/header.jsp"></jsp:include>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script src="//code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
        <script>
    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'

    });
	//var rangeDate = 31; // set limit day
	var setSdate, setEdate;
	$(function(){
		$("#from2").datepicker({
		    dateFormat: 'yy-mm-dd',
		    onSelect: function(selectDate){
		        var stxt = selectDate.split("-");
		            stxt[1] = stxt[1] - 1;
		        var sdate = new Date(stxt[0], stxt[1], stxt[2]);
		        var edate = new Date(stxt[0], stxt[1], stxt[2]);
		            /* edate.setDate(sdate.getDate() + rangeDate);
		            - 두번째달력에서 처음 날짜부터 끝 날짜 설정하는 것 */
		            edate.setDate(sdate.getDate());
		        
		        $('#to2').datepicker('option', {
		            minDate: selectDate,
		            beforeShow : function () {
		            	/* $("#to").datepicker( "option", "maxDate", edate );
		            	- 두번째달력에서 처음 날짜부터 끝 날짜 설정하는 것 */
		                setSdate = selectDate;
		                console.log(setSdate)
		        }});
		        //to 설정
		    }
		    //from 선택되었을 때
			});
		});
	
	
	$(function(){
		$("#to2").datepicker({
		    dateFormat: 'yy-mm-dd',
		    onSelect : function(selectDate){
		        setEdate = selectDate;
		        console.log(setEdate)
		    }
			});
			$('.btn2').on('click', function(e){
			    if($('input#from2').val() == ''){
			        alert('시작일을 선택해주세요.');
			        $('input#from2').focus();
			        return false;
			    }else if($('input#to2').val() == ''){
			        alert('종료일을 선택해주세요.');
			        $('input#to2').focus();
			        return false;
			    }
				/*
			    var t1 = $('input#from').val().split("-");
			    var t2 = $('input#to').val().split("-");
			    var t1date = new Date(t1[0], t1[1], t1[2]);
			    var t2date = new Date(t2[0], t2[1], t2[2]);
			    var diff = t2date - t1date;
			    var currDay = 24 * 60 * 60 * 1000;
			    if(parseInt(diff/currDay) > rangeDate){
			        alert('로그 조회 기간은 ' + rangeDate + '일을 초과할 수 없습니다.');
			        return false;
			    */
			    
			    var startDate = document.getElementById("from2").value;
			    var endDate = document.getElementById("to2").value;
			    //document.write("1"+sDate);
			    //document.write("1"+eDate);
			    sDate=startDate;
			    eDate=endDate;
	    		alert("날짜 검색이 완료되었습니다.");
			    document.location.href="./starts="+sDate+"&ends="+eDate;
				});
			});
		function visit(select){
	        var url = select.options[select.selectedIndex].getAttribute('value');
	        if(url) location.href = url;
		}
		</script>
        <div class="container" style=" border-bottom: 1px solid #CCCCCC; padding-left:0px; padding-right:0px; 
               border-bottom-color: #e04f5f">    
            <div class="col-sm-9" style="width: 100%; padding-left: 14px; padding-right:14px; ">
                  <h1 style="padding: 5px; margin-bottom: 20px;">
                      <a href="" style="color: black;">상담 요청 목록</a>
                  </h1>
                </div>
              </div>              
            <div class="container" style="margin-top: 10px;">
             <div><h2>1:1 사업자 상담</h2></div>
             <select onchange="visit(this)">
             	<option value="" disabled selected>이동할 상담 내역을 선택해주세요</option>
			    <option value="../counselAllc/"><b>1:1 고객 상담</b></option> <!-- 상대경로 -->
			    <option value="../counselAlls/"><b>1:1 사업자 상담</b></option> <!-- 상대경로 -->
			    <option><b>이동 없음</b></option>
			</select>
              <div style="margin-bottom:10px;">
  					<h4>기간 선택</h4>
						<table class="wrap">
						<tr>
							<td>
						      조회 날짜&nbsp;
							</td>
							<td>
						      <input type="text" id="from2">
							</td>
							<td>
						      &nbsp;&nbsp;~&nbsp;&nbsp; 
							</td>
							<td>
						      <input type="text" id="to2">
							</td>
						<td>
						<button class="btn2" style="margin-left:10px;">조회</button>
						</td>
						<td>
						<button style="margin-left:10px;" onclick="location.href='./'">전체조회</button>
						</td>
						</tr>	
						</table>
  				</div>
  
                <table class="table table-board table table-hover" style="border-top: 1px solid #e04f5f;border-bottom: 2px solid #ddd">
                    <colgroup>
                        <col width="15%">
                        <col width="*">
                        <col width="15%">
                        <col width="15%">
                        <col width="10%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th style="text-align: center">글 번호</th>
                            <th style="text-align: center">제목</th>
                            <th style="text-align: center">글쓴이</th>
                            <th style="text-align: center">날짜</th>
                            <th style="text-align: center">답변여부</th>
                        </tr>
                    </thead>    
                        <c:forEach var="sbean" items="${spage}" varStatus="status">
						<c:set var="stitles" value="${fn:replace(sbean.title, crcn,br) }"/>
						<c:set var="stitles" value="${fn:replace(stitles,cr,br) }"/>
                		<c:set var="stitles" value="${fn:replace(stitles,cn,br) }"/>
               			<c:set var="stitles" value="${fn:replace(stitles,' ',sp) }"/>
						<tr>
							<td class="text-center" style = "cursor:pointer;" onClick = " location.href='./store=${sbean.index }' ">
							${sbean.index}</td>
							<td class="text-center" style = "cursor:pointer;" onClick = " location.href='./store=${sbean.index }' ">
							<c:out value="${stitles }" escapeXml="false"/></td>
							<td class="text-center" style = "cursor:pointer;" onClick = " location.href='./store=${sbean.index }' ">
							${userNick[status.index] }</td>
							<td class="text-center" style = "cursor:pointer;" onClick = " location.href='./store=${sbean.index }' ">
							${sbean.calendar}</td>
							<c:choose>
								<c:when test="${sbean.comment eq null }">
									<td class="text-center" style = "cursor:pointer;" onClick = " location.href='./store=${sbean.index }' ">X</td>
								</c:when>
								<c:otherwise>
									<td class="text-center" style = "cursor:pointer;" onClick = " location.href='./store=${sbean.index }' ">O</td>
								</c:otherwise>
							</c:choose>
						</tr>
					
					</c:forEach>



                </table>
                
 	<c:choose>
		<c:when test="${spaging.numberOfRecords ne NULL and spaging.numberOfRecords ne '' and spaging.numberOfRecords ne 0}">
		<div id="paginationUI" class="text-center" style="margin-left: 37px">
			<ul class="pagination pagination-lg">
				<c:if test="${spaging.currentPageNo gt 5}">  											  <!-- 현재 페이지가 5보다 크다면(즉, 6페이지 이상이라면) -->
					<li><a href="javascript:sgoPage(${spaging.prevPageNo}, ${spaging.maxPost})">이전</a></li> <!-- 이전페이지 표시 -->
				</c:if>
				<!-- 다른 페이지를 클릭하였을 시, 그 페이지의 내용 및 하단의 페이징 버튼을 생성하는 조건문-->
					<c:forEach var="i" begin="${spaging.startPageNo}" end="${spaging.endPageNo}" step="1"> 
		            <c:choose>
		                <c:when test="${i eq spaging.currentPageNo}"> 
		                      <li class="active"><a href="javascript:sgoPage(${i}, ${spaging.maxPost})">${i}</a></li> <!-- 1페이지부터 10개씩 뽑아내고, 1,2,3페이지순으로 나타내라-->
		                </c:when>
		                	<c:otherwise>
		                    <li><a href="javascript:sgoPage(${i}, ${spaging.maxPost})">${i}</a></li> 
							</c:otherwise>
					</c:choose>
					</c:forEach>
			
				<!-- 소수점 제거 =>-->
				<fmt:parseNumber var="currentPage" integerOnly="true" value="${(spaging.currentPageNo-1)/5}"/>
				<fmt:parseNumber var="finalPage" integerOnly="true" value="${(spaging.finalPageNo-1)/5}"/>
					
				<c:if test="${currentPage < finalPage}"> <!-- 현재 페이지가 마지막 페이지보다 작으면 '다음'을 표시한다. -->
					<li><a href="javascript:sgoPage(${spaging.nextPageNo}, ${spaging.maxPost})">다음</a></li>
				</c:if> 
			</ul>
		</div>
		</c:when>
		</c:choose>

<script>
function sgoPage(spages, lines) {
    location.href = '?' + "pages=" + spages;
}
</script> 
 

            </div> 
    </body>
</html>