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
<title>秀人秀色图库 - 每日更新模特高跟丝袜美腿套图</title>
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
		<div class="row d-block d-sm-none">
			<div class="col">
				<div class="mb-2">

					<a href="/tag/国产" class="badge badge-light badge-pill">国产美女</a> <a
						href="/tag/港台" class="badge badge-light badge-pill">港台美女</a> <a
						href="/tag/日韩" class="badge badge-light badge-pill">日韩美女</a> <a
						href="/album/国产名站" class="badge badge-light badge-pill">国产名站</a> <a
						href="/album/港台名站" class="badge badge-light badge-pill">港台名站</a> <a
						href="/album/日韩名站" class="badge badge-light badge-pill">日韩名站</a> <a
						href="/album/国产模特" class="badge badge-light badge-pill">国产模特</a> <a
						href="/album/港台模特" class="badge badge-light badge-pill">港台模特</a> <a
						href="/album/日韩模特" class="badge badge-light badge-pill">日韩模特</a> <a
						href="/album/风格" class="badge badge-light badge-pill">风格</a>

				</div>
				<form action="/search" method="GET">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="搜索图库"
							name="q">
						<div class="input-group-append">
							<button class="btn btn-outline-primary" type="submit">搜索</button>
						</div>
					</div>
				</form>
			</div>
		</div>

		<#if recommendList?? && (recommendList?size>0)>
			<div class="row mr-0">
				<div class="col-12">
					<h6>
						<span class="badge badge-pill badge-danger">今日推荐</span>
					</h6>
				</div>

				<#list recommendList as item>
					<div class="col-md-2 col-4 mb-3 pr-0">
						<a href="/p/${item.id}" target="_blank"
							class="text-dark text-decoration-none"> <img
							src="${item.cover}" class="w-100">
							<p class="my-1">${item.title}</p>
						</a>
					</div>
				</#list>

			</div>
		</#if>


		<div class="row mr-0">
			<div class="col-12">
				<h6>
					<span class="badge badge-pill badge-warning">最新发布</span>
				</h6>
			</div>
			<#list pageList as item>
				<div class="col-md-2 col-4 mb-3 pr-0">
					<a href="/p/${item.id}" target="_blank"
						class="text-dark text-decoration-none"> <img
						src="${item.cover}" class="w-100">
						<p class="my-1">${item.title}</p>
					</a>
				</div>
			</#list>

			<div class="col-12">
				<#include "page.ftl"/>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.15.0/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"></script>

</body>
</html>
