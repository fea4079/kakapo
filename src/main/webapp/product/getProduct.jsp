<%@ page contentType="text/html; charset=euc-kr"%>
<%-- <%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.Search" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">

<!-- 참조 : http://getbootstrap.com/css/   참조 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- Bootstrap Dropdown Hover CSS -->
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">

<!-- Bootstrap Dropdown Hover JS -->
<script src="/javascript/bootstrap-dropdownhover.min.js"></script>

<!--  ///////////////////////// CSS ////////////////////////// -->
<style>
body>div.container {
	border: 3px solid #D6CDB7;
	margin-top: 20px;
}
</style>


<script type="text/javascript">
	
	$(function() {
		var menu = $("#menu").val();
		alert("getProduct menu="+menu) 
		
		$("button:contains('확인')").on("click", function(){
			self.location = "/product/listProduct?prodNo="+$(this).children($("#prodNo")).val()+"&menu="+menu;
			/* self.location = "/product/listProduct?prodNo=${product.prodNo}&menu=${param.menu}"; */
		})
		
		$("button:contains('추가등록')").on("click", function(){
			self.location = "/product/addProductView.jsp";
		})
	})	
	</script>

</head>

<body>
	<jsp:include page="/layout/toolbar.jsp" />

	<div class="container">


		<div class="page-header">
			<h3 class=" text-info">상품관리</h3>
		</div>

		<div class="row">
			<div class="col-xs-4 col-md-2">
				<strong>상 품 명</strong>
			</div>
			<div class="col-xs-8 col-md-4">${product.prodName}</div>
		</div>

		<div class="row">
			<div class="col-xs-4 col-md-2">
				<strong>상품상세정보</strong>
			</div>
			<div class="col-xs-8 col-md-4">${product.prodDetail}</div>
		</div>

		<div class="row">
			<div class="col-xs-4 col-md-2">
				<strong>제조일자</strong>
			</div>
			<div class="col-xs-8 col-md-4">${product.manuDate}</div>
		</div>

		<div class="row">
			<div class="col-xs-4 col-md-2">
				<strong>가 격</strong>
			</div>
			<div class="col-xs-8 col-md-4">${product.price}</div>
		</div>

		<div class="row">
			<div class="col-xs-4 col-md-2">
				<strong>상품이미지</strong>
			</div>
			<div class="col-xs-8 col-md-4">
				<img src="/images/uploadFiles/${product.fileName}" /> <input
					type="hidden" name="fileName" />
			</div>
		</div>

		<hr />
		<div class="row">
			<div class="col-md-12 text-center ">
				<button type="button" class="btn btn-primary">확인</button>
				<input type="hidden" id="prodNo" name="prodNo"
					value="${product.prodNo}" /> <input type="hidden" id="menu"
					name="managa" value="${param.menu}" />

			</div>
		</div>

		<div class="row">
			<div class="col-md-12 text-center ">
				<button type="button" class="btn btn-primary">추가등록</button>
			</div>
		</div>

		<br />

		<%-- <table width="100%" border="0" cellspacing="0" cellpadding="0"
		style="margin-top: 10px;">
		<tr>
			<td width="53%"></td>
			<td align="right">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="17" height="23"><img src="/images/ct_btnbg01.gif"
							width="17" height="23" /></td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01"
							style="padding-top: 3px;">
						<input type="hidden" id="prodNo" name="prodNo" value="${product.prodNo}" /> 
						<input type="hidden" id="menu" name="menu" value="${param.menu}" /> 
							확인</td>
						<td width="14" height="23"><img src="/images/ct_btnbg03.gif"
							width="14" height="23" /></td>
						<td width="17" height="23"><img src="/images/ct_btnbg01.gif"
							width="17" height="23" /></td>
						<td background="/images/ct_btnbg02.gif" class="ct_btn01"
							style="padding-top: 3px;">추가등록</td>
						<td width="14" height="23"><img src="/images/ct_btnbg03.gif"
							width="14" height="23" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table> --%>

	</div>
	<% System.out.println("getProduct.jsp 4444444444444444444444444444444444444444444"); %>
</body>
</html>
