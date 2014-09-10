<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8">
	$().ready(function() {
		$(".inline").colorbox({inline:true,width:"30%"});
	});
	function setUrl(url) {
		document.getElementById("path").value = url;
		$.colorbox.close();
	}
	
</script>
<c:if test="${!empty noticefail}">
	<div class="alert alert-danger">
		<strong>Oop!</strong> Failed implementation
	</div>
</c:if>
<c:if test="${!empty noticesuccess}">
	<div class="alert alert-success">
		<strong>Well done!</strong> Successful implementation
	</div>
</c:if>
<c:if test="${!empty ulist.entities}">
	<form:form class="custom-form" method="post"
		action='${pageContext.request.contextPath}/tags/index'
		commandName="tags">
		<h3>List of Tag</h3>
			<table class="table-data">
				<thead>
					<tr>
						<th>#</th>
						<th>Name</th>
						<th>Desciption</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${ulist.entities}" var="data">
						<c:set var="count" value="${count + 1}" scope="page" />
						<tr>
							<td><c:out value="${count}"></c:out></td>
							<td><c:out value="${data.name}"></c:out></td>
							<td><c:out value="${data.description}"></c:out></td>
							<td><a
								href="${pageContext.request.contextPath}/tags/delete/${data.tagID}"><img
									src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">
				<c:if test="${!ulist.nav.firstPage}">
					<a href="${pageContext.request.contextPath}/tags/index">First</a>&nbsp;
			</c:if>
				<c:forEach var="i" items="${ulist.nav.indexList}">
					<c:choose>
						<c:when test="${i != ulist.nav.currentPage}">
							<a href="${pageContext.request.contextPath}/tags/index/${i}">${i}</a>&nbsp;
					</c:when>
						<c:otherwise>
							<b>${i}</b>&nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${!ulist.nav.lastPage}">
					<a
						href="${pageContext.request.contextPath}/tags/index/${ulist.nav.pageCount}">Last</a>
				</c:if>
			</div>
	</form:form>
</c:if>