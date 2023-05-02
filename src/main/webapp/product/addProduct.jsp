<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html lang="ko">

<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--  <script type="text/javascript" src="../javascript/calendar.js"> -->
<link href="/css/animate.min.css" rel="stylesheet">
<link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">

<!--  ///////////////////////// CSS ////////////////////////// -->
<script src="/javascript/bootstrap-dropdownhover.min.js"></script>

<style>
body>div.container {
	border: 3px solid #D6CDB7;
	margin-top: 20px;
}
</style>

<script type="text/javascript">
	function fncAddProduct(){
		//Form 유효성 검증
	 	var prodName =$("input[name=prodName]").val();
	 	var prodDetail =$("input[name=prodDetail]").val();
	 	var manuDate = $("input[name=manuDate]").val();
	 	var price = $("input[name=price]").val();
	
		if(prodName == null || prodName.length<1){
			alert("상품명은 반드시 입력하여야 합니다.");
			return;
		}r
		if(prodDetail == null || prodDetail.length<1){
			alert("상품상세정보는 반드시 입력하여야 합니다.");
			return;
		}
		if(manuDate == null || manuDate.length<1){
			alert("제조일자는 반드시 입력하셔야 합니다.");
			return;
		}
		if(price == null || price.length<1){
			alert("가격은 반드시 입력하셔야 합니다.");
			return;
		}
	
		$("form").attr("method" , "POST").attr("action" , "/product/addProduct").submit();
	}
	$(function() {
		$(".ct_btn01:contains('수정')").on("click", function(){
			fncAddProduct();
		})
	})
	
	$(function() {
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			//==> 1 과 3 방법 조합 : $("tagName.className:filter함수") 사용함.	
			 $( "td.ct_btn01:contains('확인')" ).on("click" , function() {
					//Debug..
					//alert(  $( "td.ct_btn01:contains('취소')" ).html() );
				/* $("form")[0].reset(); */
				history.go(-1);
			});
	});

/* function resetData(){
	document.detailForm.reset();
} */
</script>
</head>

<body>
	<jsp:include page="/layout/toolbar.jsp" />

	<div class="navbar  navbar-default">
		<div class="container">
			<a class="navbar-brand" href="http://192.168.0.159:8080/">Model2
				MVC Shop</a>
		</div>
	</div>

	<div class="container">

		<h1 class="bg-primary text-center">상품등록</h1>

		<form class="form-horizontal">
			<input type="hidden" id="prodNo" name="prodNo"
				value="${product.prodNo}" /> <input type="hidden" id="menu"
				name="menu" value="${param.menu}" />

			<div class="form-group">
				<label for="prodName" class="col-sm-offset-1 col-sm-3 control-label">상품명</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="prodName"
						name="prodName" value="${product.prodName}">
				</div>
			</div>

			<div class="form-group">
				<label for="prodDetail"
					class="col-sm-offset-1 col-sm-3 control-label">상품상세정보</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="prodDetail"
						name="prodDetail" value="${product.prodDetail }">
				</div>
			</div>

			<div class="form-group">
				<!--  data-provide="datepicker"
				data-date-format="yyyy-mm-dd"> -->
				<!-- input-group date -->
				<label for="manuDate" class="col-sm-offset-1 col-sm-3 control-label">제조일자</label>
				<div class="col-sm-4">
					<input type="date" class="form-control" id="manuDate"
						name="manuDate" value="${product.manuDate}"> <span
						class="glyphicon glyphicon-th"></span>
				</div>
			</div>

			<div class="form-group">
				<label for="price" class="col-sm-offset-1 col-sm-3 control-label">가격</label>
				<div class="col-sm-4">
					<input type="number" class="form-control" id="price" name="price"
						value="${product.price}">
				</div>
			</div>

			<div class="form-group">
				<label for="fileName"
					class="col-sm-offset-1 col-sm-3 control-label form-label">상품이미지</label>
				<div class="col-sm-4">
					<input type="file" class="form-control" id="uploadFile" name="file">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-4  col-sm-4 text-right">
					<button type="button" class="btn btn-primary" id="addProductD">수정</button>
					<a class="btn btn-primary btn" id="cancel" href="#" role="button">확인</a>
				</div>
			</div>
		</form>
	</div>
	<% System.out.println("addProduct.jsp 222222222222222222222222222222222"); %>

</body>
</html>

