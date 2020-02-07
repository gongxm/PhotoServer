<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${page.totalPage>1}">
	<nav aria-label="Page navigation example">
		<ul class="pagination flex-wrap">
			<c:if
				test="${page.previousPage!=0 && page.totalPage>10 && page.currentPage>6}">
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/1">首页</a></li>
			</c:if>
			<c:if test="${page.previousPage!=0}">
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/${page.previousPage}">上一页</a></li>
			</c:if>
			<c:forEach var="item" varStatus="status" begin="${page.startIndex}"
				end="${page.endIndex}">
				<c:if test="${page.currentPage==status.index}">
					<li class="page-item active"><a class="page-link"
						href="/${page.baseUrl}/${status.index}">${status.index}</a></li>
				</c:if>
				<c:if test="${page.currentPage!=status.index}">
					<li class="page-item"><a class="page-link"
						href="/${page.baseUrl}/${status.index}">${status.index}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${page.nextPage!=0}">
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/${page.nextPage}">下一页</a></li>
			</c:if>
			<c:if
				test="${page.nextPage!=0 && page.totalPage>10 && page.currentPage<page.totalPage-4}">
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/${page.totalPage}">尾页</a></li>
			</c:if>
		</ul>
	</nav>
</c:if>