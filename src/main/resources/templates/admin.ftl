<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="applicable-device" content="pc,mobile">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<!--jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边)-->
<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/static/js/bootstrap.js"></script>
<script src="/static/js/feather.min.js"></script>
<link href="/static/css/bootstrap.css" rel="stylesheet">

<script src="/static/js/admin.js"></script>

<style>

.task-box{
	margin:50px auto;
	width:50%;
	height:500px;
	border:1px solid #ff0000;
}
  
</style>

<title>图片管理系统</title>
</head>
<body>
	<div class="container-fluid">
		<h1 class="text-center">采集功能</h1>
		<div class="text-center" role="toolbar"
			aria-label="Toolbar with button groups">
			<div class="btn-group mr-2" role="group" aria-label="First group">
				<button type="button" id="create-url-list" class="btn btn-primary">生成采集列表数据</button>
			</div>
			<div class="btn-group mr-2" role="group" aria-label="Second group">
				<button type="button" id="collect-image-group"
					class="btn btn-success">采集图片组信息</button>
			</div>
			<div class="btn-group mr-2" role="group" aria-label="Third group">
				<button type="button" id="collect-image-page"
					class="btn btn-warning">采集图片页面地址</button>
			</div>
			<div class="btn-group mr-2" role="group" aria-label="Fourth group">
				<button type="button" id="collect-image-info" class="btn btn-primary">采集图片地址</button>
			</div>

			<div class="btn-group mr-2" role="group" aria-label="Five group">
				<button type="button" id="collect-image-file" class="btn btn-danger">采集图片文件</button>
			</div>
		</div>
		<div class="text-center  task-box"> 
			线程任务信息:  <br/>
			
			<div id="count">运行中的任务数量: 0</div>
			<div id="size">等待中的任务数量: 0</div>
			
		</div>
		
	</div>
</body>
</html>
