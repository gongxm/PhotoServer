<#if (page.totalPage>1)>
	<nav aria-label="Page navigation example">
		<ul class="pagination flex-wrap">
			<#if (page.previousPage!=0 && page.totalPage>10 && page.currentPage>6)>
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/1">首页</a></li>
			</#if>
			<#if (page.previousPage!=0)>
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/${page.previousPage}">上一页</a></li>
			</#if>
			<#list page.startIndex..page.endIndex as index>
				<#if (page.currentPage==index)>
					<li class="page-item active"><a class="page-link"
						href="/${page.baseUrl}/${index}">${index}</a></li>
				</#if>
				<#if (page.currentPage!=index)>
					<li class="page-item"><a class="page-link"
						href="/${page.baseUrl}/${index}">${index}</a></li>
				</#if>
			</#list>
			<#if (page.nextPage!=0)>
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/${page.nextPage}">下一页</a></li>
			</#if>
			<#if (page.nextPage!=0 && page.totalPage>10 && page.currentPage<page.totalPage-4)>
				<li class="page-item"><a class="page-link"
					href="/${page.baseUrl}/${page.totalPage}">尾页</a></li>
			</#if>
		</ul>
	</nav>
</#if>