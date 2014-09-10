<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${!empty pathBar}">
<!-- Being Page Title -->
<div class="container">
	<div class="page-title clearfix">
		<div class="row">
			<div class="col-md-12">
				<h6>
					<a href="${pageContext.request.contextPath}">Home</a>
				</h6>
				
				<h6>
					<span class="page-active">${pathBar}</span>
				</h6>
				
			</div>
		</div>
	</div>
</div>
</c:if>