
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
	<style>
	  body {
            padding-top : 50px;
        }
    </style>

<script type="text/javascript">
	function fncGetProductList(currentPage, menu){
		$("#currentPage").val(currentPage);
		var menu = $("#menu").val(menu);
		alert("list ment="+menu)
		$("form").attr("method", "POST").attr("action", "/product/listProduct").submit();
	}	
	
	$(function() {
		var menu = $("#menu").val(); 
		var prodNo = $("#prodNo").val();
		/* alert(prodNo) */
	
		var currentPage = $("#currentPage").val();
			
		$( ".btn.btn-default:contains('검색')" ).on("click", function() {
			fncGetProductList(currentPage,menu);
		})
								
		$( "td:nth-child(2)").on("click", function() {
			/* var prodNo = $(this).children('#prodNo').val();
			alert("prodNo= "+prodNo) */
			/* alert("prodNo="+$(this).text().trim(); */
			alert("menu="+menu)
			
			  self.location = "/product/getProduct?prodNo="+$(this).children($("#prodNo")).val()+"&menu="+menu;  
			/* self.location = "/product/getProduct?prodNo="+$("#prodNo").val()+"&menu="+menu; */
			 /*  self.location = "/product/getProduct?prodNo="+$(this).children($("input[name=prodNo]")).val()+"&menu="+menu;  */
			
		});
		
		$( ".ct_list_pop td:nth-child(3)" ).css("color" , "blue");
		$("h7").css("color" , "red");
		
		$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
		 
		 $( "td" ).on( "mouseover", function() {
			   $( this ).css( "color", "#FF8000" ); 			  
			});
		 $('td').on("mouseout", function(){
		      $(this).css("color","#505050");
		 });
	})

</script>
</head>
	<jsp:include page="/layout/toolbar.jsp" />

<div class="container">

<form name="detailForm">

	<div class="page-header text-info">
		<c:choose>
			<c:when test="${param.menu eq 'manage' }"> 
			<h3>상품관리</h3>
			</c:when>
			<c:when test="${param.menu eq 'search' }">
			<h3>상품검색</h3>
			</c:when>
		</c:choose>
	</div>

	<div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		    	</p>
		    </div>
		    
		    <div class="col-md-6 text-right">
			    <form class="form-inline" name="detailForm">
			    
				  <div class="form-group">
				    <select class="form-control" name="searchCondition" >
						<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : ""}>상품번호</option>
						<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : ""}>상품명</option>
						<option value="2" ${! empty search.searchCondition && search.searchCondition==2 ? "selected" : ""}>가격</option>
					</select>
				  </div>
				  
				  <div class="form-group">
				    <label class="sr-only" for="searchKeyword">검색어</label>
				    <input type="text" class="form-control" id="searchKeyword" name="searchKeyword"  placeholder="검색어"
				    			 value="${! empty search.searchKeyword ? search.searchKeyword : '' }"  >
				  </div>
				  
				  <button type="button" class="btn btn-default">검색</button>
				  
				  <!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				  <input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>
				  
				</form>
	    	</div>
	    	
		</div>
		
		 
	<table class="table table-hover table-striped" >
      
        <thead>
          <tr>
            <th align="center">No</th>
            <th align="left" >상품명</th>
            <th align="left">상세정보</th>
            <th align="left">상품가격</th>
            <th align="left">현재상태</th>
          </tr>
        </thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var="product" items="${list}">
			<c:set var="i" value="${ i+1 }" />
			<tr>
			  <td align="center">${ i }</td>
			  <td align="left"  title="클릭하세요">${product.prodName}
			  	<input type="hidden" id="prodNo" name="prodNo" value="${product.prodNo}"/>
			  	<input type="hidden" id="menu" name="menu" value="${param.menu}"/>
			  	</td>
			 	
			  <td align="left">${product.prodDetail}</td>
			  <td align="left">${product.price}</td>
			  <td align="left">${product.proTranCode}</td>
			  <td align="left">
			  	<i class="glyphicon glyphicon-ok" id= "${product.prodNo}"></i>
			  	
			  </td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
    </div>
</form>


		<jsp:include page="../common/pageNavigator1.jsp"/>
<% System.out.println("listProduct.jsp 555555555555555555555555555555"); %>
</body>
</html>




<%--  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">
			상품명<br>
			<h7> 상품명 click:상세정보</h7>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">등록일</td>
		<td class="ct_line02"></td>
		<!-- <td class="ct_list_b" width="150">현재상태</td>
		<td class="ct_line02"></td> -->
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
				<td align="left" title="클릭하세요">
				<input type="hidden" id="prodNo" name="prodNo" value="${product.prodNo}"/>
			 	<input type="hidden" id="menu" name="menu" value="${param.menu}"/>
				<c:choose>
			 		<c:when test="${param.menu eq 'manage' }"> 
						 ${product.prodName} 
					</c:when>
					<c:when test="${param.menu eq 'search' }"> 
						 ${product.prodName}
					</c:when>
				</c:choose>
				
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}
			</td>		
		</tr>
		<tr>
		<td id="${product.prodNo}" colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>
			
		
			<jsp:include page="../common/pageNavigator1.jsp"/>
    	</td>
	</tr>
</table>  --%>
<!--  페이지 Navigator 끝 -->
