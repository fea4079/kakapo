<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="UTF-8">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	   
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
	body>div.container {
		border: 3px solid #D6CDB7;
		margin-top: 20px;
	}
</style>
    
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">
	function fncUpdateProduct() {
		/* var menu = $("#menu").val(); */
		var menu = $("input:hidden[name='menu']").val();
		alert("update menu="+menu)
		
		$("form").attr("method" , "POST").attr("action" , "/product/updateProduct").submit();
		
	}
	
	$(function() {
		
		$("#addProductD").on("click", function(){
			fncUpdateProduct();
		})
	})
	$(function() {
			 $("#cancel" ).on("click" , function() {
				/* $("form")[0].reset(); */
				 history.go(-1); 
			});
			
	});
</script>
</head>

<body >

	<jsp:include page="/layout/toolbar.jsp" />
	
	<div class="navbar  navbar-default">
		<div class="container">
			<a class="navbar-brand" href="http://192.168.0.159:8080/">Model2
				MVC Shop</a>
		</div>
	</div>
	
	<div class="container">
		
		

		<h1 class="bg-primary text-center">상품수정</h1>
	    
	    <form class="form-horizontal" enctype="multipart/form-data">
	    
	    	<div class="form-group">
		    <label for="prodName" class="col-sm-offset-1 col-sm-3 control-label">상 품 명</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="prodName" name="prodName" value="${product.prodName }" >
		    </div>
		 	</div>
		 	
		 	<div class="form-group">
		    	<label for="prodDetail" class="col-sm-offset-1 col-sm-3 control-label">상품상세정보</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="prodDetail" name="prodDetail" value="${product.prodDetail}" >
			    </div>
		 	</div>
		 	
		 	<div class="form-group" > <!-- data-provide="datepicker" data-date-format="yyyy/mm/dd" -->
		    <label for="manuDate" class="col-sm-offset-1 col-sm-3 control-label">제조일자</label>
		    <div class="col-sm-4">
		      <input type="date" class="form-control" id="manuDate" name="manuDate" value="${product.manuDate}" >
		    </div>
		 	</div>
		 	
		 	<div class="form-group">
		    <label for="price" class="col-sm-offset-1 col-sm-3 control-label">가    격</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="price" name="price" value="${product.price}" >
		    </div>
		 	</div>
		 	
		 	 <div class="form-group">
			    <label for="file" class="col-sm-offset-1 col-sm-3 control-label">상품이미지</label>
			    <div class="col-sm-4">  
			      <input type="file" class="form-control" id="uploadFile" name="file" value="${product.fileName}" >
			      <img src="/images/uploadFiles/${product.fileName}" />
			      <input type="hidden" name="fileName" />
			    </div> 
		    
		 	</div>
			
			<div class="form-group">
				<div class="col-sm-offset-4  col-sm-4 text-right">
					<button type="button" class="btn btn-primary" id="addProductD">수정</button>
						<input type="hidden" name="prodNo" value="${product.prodNo}" /> 
						<input type="hidden" name="menu" value="${param.menu}" /> 
					<a class="btn btn-primary btn" id="cancel" href="#" role="button">취소</a>
				</div>
			</div>
		</form>		
		
	</div>

</body>
</html>