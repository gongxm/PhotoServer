$(function() {
	$('body').on('click','#create-url-list',function(){
		$.ajax({
			url:'/action/create_url_list.action',
			data:{},
			method:'post',
			success:function(data){
				if(data.errcode==1){
					alert('开始生成数据,请稍候!')
				}else{
					alert('请求失败, 请检查!')
				}
			},
			error:function(){
				alert('网络请求失败, 请检查!')
			}
		})
	})
	
	$('body').on('click','#collect-image-group',function(){
		$.ajax({
			url:'/action/collect_image_group_info.action',
			data:{},
			method:'post',
			success:function(data){
				if(data.errcode==1){
					alert('开始采集图片组信息,请关注后台!')
				}else{
					alert('请求失败, 请检查!')
				}
			},
			error:function(){
				alert('网络请求失败, 请检查!')
			}
		})
	})
	
	$('body').on('click','#collect-image-page',function(){
		$.ajax({
			url:'/action/collect_image_page.action',
			data:{},
			method:'post',
			success:function(data){
				if(data.errcode==1){
					alert('开始采集图片页面信息,请关注后台!')
				}else{
					alert('请求失败, 请检查!')
				}
			},
			error:function(){
				alert('网络请求失败, 请检查!')
			}
		})
	})
	
	
	$('body').on('click','#collect-image-info',function(){
		$.ajax({
			url:'/action/collect_image_info.action',
			data:{},
			method:'post',
			success:function(data){
				if(data.errcode==1){
					alert('开始采集图片地址,请关注后台!')
				}else{
					alert('请求失败, 请检查!')
				}
			},
			error:function(){
				alert('网络请求失败, 请检查!')
			}
		})
	})
	$('body').on('click','#collect-image-file',function(){
		$.ajax({
			url:'/action/collect_image_file.action',
			data:{},
			method:'post',
			success:function(data){
				if(data.errcode==1){
					alert('开始采集图片文件,请关注后台!')
				}else{
					alert('请求失败, 请检查!')
				}
			},
			error:function(){
				alert('网络请求失败, 请检查!')
			}
		})
	})
});