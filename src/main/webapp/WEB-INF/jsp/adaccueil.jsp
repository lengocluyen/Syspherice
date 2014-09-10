<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<h3 style="padding-left:20px;"> List of Application</h3>
<div id="cpanel" style="margin:20px;">

		<div class="icon">
			<a href="${pageContext.request.contextPath}/exceldatadoc/index"><img
				id="item05"
				src=<c:url value="/resources/images/icons/icon-48-component.png"/>
				style="border-width: 0px;"> <span class="title">Excel
					and project list</span> </a>
		</div>
		<div class="icon">
			<a href="${pageContext.request.contextPath}/exceldatadoc/create"><img
				id="item05"
				src=<c:url value="/resources/images/icons/screen-add.png"/>
				style="border-width: 0px;"> <span class="title">Create a excel
					and project</span> </a>
		</div>
		<div class="icon">
			<a href="${pageContext.request.contextPath}/admin/impordata"><img
				id="item01"
				src='<c:url value="/resources/images/icons/icon-48-article-add.png"/>'
				style="border-width: 0px;"> <span class="title">Import
					Data</span> </a>
		</div>

		<div class="icon">
			<a href="${pageContext.request.contextPath}/searchtype/index"><img
				id="item01"
				src='<c:url value="/resources/images/icons/screen-sonic.png"/>'
				style="border-width: 0px;"> <span class="title">Type of Search</span>
			</a>
		</div>
		
		<div class="icon">
			<a href="${pageContext.request.contextPath}/searchtype/chooseproject"><img
				id="item01"
				src='<c:url value="/resources/images/icons/screen-default.png"/>'
				style="border-width: 0px;"> <span class="title">Create a type of search</span>
			</a>
		</div>

<div class="icon">
			<a href="${pageContext.request.contextPath}/imagedata/index"><img
				id="item01"
				src='<c:url value="/resources/images/icons/screen-media.png"/>'
				style="border-width: 0px;"> <span class="title">List of Image data</span>
			</a>
		</div>
		
		<div class="icon">
			<a href="${pageContext.request.contextPath}/imagedata/import"><img
				id="item01"
				src='<c:url value="/resources/images/icons/x-js-com_jcommerce.png"/>'
				style="border-width: 0px;"> <span class="title">Import image data</span>
			</a>
		</div>


<div class="icon">
			<a href="${pageContext.request.contextPath}/tags/index"><img
				id="item01"
				src='<c:url value="/resources/images/icons/screen-tags.png"/>'
				style="border-width: 0px;"> <span class="title">Tags</span>
			</a>
		</div>
		
		<div class="icon">
			<a href="${pageContext.request.contextPath}/itemtag/index"><img
				id="item01"
				src='<c:url value="/resources/images/icons/tpl-khepri-module.png"/>'
				style="border-width: 0px;"> <span class="title">Item Tags</span>
			</a>
		</div>


		<div class="icon">
			<a href="${pageContext.request.contextPath}/account/index"><img
				id="item01"
				src='<c:url value="/resources/images/icons/icon-48-user.png"/>'
				style="border-width: 0px;"> <span class="title">Account</span>
			</a>
		</div>

		<div class="icon">
			<a href="${pageContext.request.contextPath}/contact/index"><img
				id="item03"
				src='<c:url value="/resources/images/icons/icon-48-massmail.png"/>'
				style="border-width: 0px;"> <span class="title">Contact</span>
			</a>
		</div>

		<div class="icon">
			<a href="${pageContext.request.contextPath}/admin/filemanage"><img
				id="item04"
				src='<c:url value="/resources/images/icons/icon-48-section.png"/>'>
				<span class="title">File Manage</span> </a>
		</div>
		<div class="icon">
			<a href="${pageContext.request.contextPath}/admin/introduction"><img
				id="item05"
				src=<c:url value="/resources/images/icons/icon-48-menumgr.png"/>
				style="border-width: 0px;"> <span class="title">Introduction</span>
			</a>
		</div>

</div>
<div style="clear:both;"></div>