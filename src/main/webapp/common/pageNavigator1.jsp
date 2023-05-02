<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container text-center">


	<nav>
		<!-- 크기조절 :  pagination-lg pagination-sm-->
		<ul class="pagination">
			<c:set var="product" value="product" scope="request" />
			<c:set var="search" value="search" scope="request" />
			<c:set var="menu" value="${param.menu}" scope="request" />

			<!--  <<== 좌측 nav -->
			<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
				<li class="disabled">
			</c:if>
			<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
				<li>
			</c:if>
			<a
				href="javascript:fncGetProductList('${ resultPage.currentPage-1}','${param.menu}')"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a>
			</li>

			<!--  중앙  -->
			<c:forEach var="i" begin="${resultPage.beginUnitPage}"
				end="${resultPage.endUnitPage}" step="1">

				<c:if test="${ resultPage.currentPage == i }">
					<!--  현재 page 가르킬경우 : active -->
					<li class="active"><a
						href="javascript:fncGetProductList('${ i }','${param.menu}');">${ i }<span
							class="sr-only">(current)</span></a></li>
				</c:if>

				<c:if test="${ resultPage.currentPage != i}">
					<li><a
						href="javascript:fncGetProductList('${ i }','${param.menu}');">${ i }</a>
					</li>
				</c:if>
			</c:forEach>

			<!--  우측 nav==>> -->
			<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
				<li class="disabled">
			</c:if>
			<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
				<li>
			</c:if>
			<a
				href="javascript:fncGetProductList('${resultPage.endUnitPage+1}','${param.menu}')"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a>
			</li>
		</ul>
	</nav>

</div>



<div class="container">
	<nav>
		<ul class="pager">
			<li><a href="#">Previous</a></li>
			<li><a href="#">Next</a></li>
		</ul>
	</nav>
</div>


<div class="container">
	<nav>
		<ul class="pager">
			<li class="previous disabled"><a href="#"><span
					aria-hidden="true">&larr;</span> Older</a></li>
			<!-- <li class="previous"><a href="#"><span aria-hidden="true">&larr;</span> Older</a></li>  -->
			<li class="next"><a href="#">Newer <span aria-hidden="true">&rarr;</span></a></li>
		</ul>
	</nav>
</div>


<%--  	<c:set var = "product" value="product" scope="request"/>
	<c:set var = "search" value="search" scope="request"/>
	<c:set var = "menu" value="${param.menu}" scope="request"/>
	
	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			◀ 이전
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetProductList('${ resultPage.currentPage-1}','${param.menu}')">◀ 이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetProductList('${i}','${param.menu}');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}','${param.menu}')">이후 ▶</a>
	</c:if>  --%>