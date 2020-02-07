<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="applicable-device" content="pc,mobile">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/open-iconic@1.1.1/font/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/static/css/style.css?v=<%out.print(Math.random());%>" />
<title>${info.title}-国产- 秀人秀色图库</title>

<style type="text/css">
	.pageLine{
		clear:both;
	}
</style>

</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-light bg-white border-bottom shadow-sm mb-3 sticky-top">
		<div class="container">
			<a class="navbar-brand" href="/"> <img
				src="/static/images/bootstrap-solid.svg?v=<%out.print(Math.random());%>"
				width="30" height="30" class="d-inline-block align-top" alt="">
				秀人秀色图库
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item active"><a class="nav-link" href="/">首页
							<span class="sr-only">(current)</span>
					</a></li>

					<li class="nav-item"><a class="nav-link text-dark"
						href="/tag/国产">国产美女</a></li>

					<li class="nav-item"><a class="nav-link text-dark"
						href="/tag/港台">港台美女</a></li>

					<li class="nav-item"><a class="nav-link text-dark"
						href="/tag/日韩">日韩美女</a></li>


					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle text-dark" href="#"
						id="navbarDropdown" role="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> 名站 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">

							<a class="dropdown-item" href="/album/国产名站">国产名站</a> <a
								class="dropdown-item" href="/album/港台名站">港台名站</a> <a
								class="dropdown-item" href="/album/日韩名站">日韩名站</a>

						</div></li>

					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle text-dark" href="#"
						id="navbarDropdown" role="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> 模特 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">

							<a class="dropdown-item" href="/album/国产模特">国产模特</a> <a
								class="dropdown-item" href="/album/港台模特">港台模特</a> <a
								class="dropdown-item" href="/album/日韩模特">日韩模特</a>

						</div></li>

					<li class="nav-item"><a class="nav-link text-dark"
						href="/album/风格">风格</a></li>
				</ul>
				<form class="form-inline my-2 my-lg-0" action="/search" method="GET">
					<input class="form-control mr-sm-2" type="search"
						placeholder="搜索图库" aria-label="Search" name="q">
					<button class="btn btn-outline-primary my-2 my-sm-0" type="submit">搜索</button>
				</form>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<nav aria-label="breadcrumb">
					<ol class="breadcrumb p-0 bg-transparent">
						<li class="breadcrumb-item"><a href="/"
							class="text-decoration-none">首页</a></li>

						<c:forEach items="${tags}" var="item" begin="0" end="2">

							<li class="breadcrumb-item"><a href="/tag/${item.tag}"
								class="text-decoration-none">${item.tag}</a></li>

						</c:forEach>

						<li class="breadcrumb-item active" aria-current="page">正文</li>
					</ol>
				</nav>
				<h1 class="h6 font-weight-bold my-3">${info.title}</h1>
				<p>
					时间：
					<fmt:formatDate value="${info.updateTime}" pattern="yyyy-MM-dd" />
				</p>
				<div class="row content">
					<div class="col text-center">
						<c:forEach items="${images}" var="item">
							<p>
								<a href="${item.url}" target="_blank" rel="nofollow"><img
									src="${item.url}" alt="" class="mw-100"></a>
							</p>
						</c:forEach>
					</div>
				</div>

				<div class="mb-3">
					<c:forEach items="${tags}" var="item">
						<a href="/tag/${item.tag}" class="badge badge-warning border">${item.tag}</a>
					</c:forEach>
				</div>


				<div class="input-group mb-3" style="width: 200px; float: left;">
					<div class="input-group-prepend">
						<label class="input-group-text" for="pageNumber">每页显示数量:</label>
					</div>
					<select class="custom-select" id="pageNumber">
						<option value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
					</select>
				</div>
				<div class='pageLine'>
					<%@ include file="page.jsp"%>
				</div>
			</div>
			<div class="col-12 mb-3">
				<h6 class="font-weight-bold">猜你喜欢</h6>
				<div class="row mr-0 loading">

					<c:forEach items="${recommendList}" var="item">
						<div class="col-md-2 col-4 mb-3 pr-0">
							<a href="/s/${item.id}" target="_blank"
								class="text-dark text-decoration-none"> <img
								src="${item.cover}" class="w-100">
							</a>
						</div>
					</c:forEach>

				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.15.0/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>
	<script src="/static/js/load.min.js?v=<%out.print(Math.random());%>"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/js-cookie@beta/dist/js.cookie.min.js"></script>


	<script type="text/javascript">
		$(function() {
			$("#pageNumber").change(function() {
				var number = $("#pageNumber").val();
				setCookie('pageNumber', number);
				window.location.reload()
			})

			$("#pageNumber").val("${pageNumber}");
		})

		function setCookie(name, value) {
			var Days = 30;
			var exp = new Date();
			exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
			document.cookie = name + "=" + escape(value) + ";expires="
					+ exp.toGMTString();
		}
	</script>
</body>
</html>
