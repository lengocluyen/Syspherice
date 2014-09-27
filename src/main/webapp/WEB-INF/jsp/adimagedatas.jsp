<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		action='${pageContext.request.contextPath}/imagedata'
		commandName="imagedata">
		<h3>Image Data List</h3>
			<table class="table-data">
				<thead>
					<tr>
						<th>Plant ID</th>
						<th>Name</th>
						<th>Image</th>
						<th>Type</th>
						<th>Order</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${ulist.entities}" var="data">
						<c:set var="count" value="${count + 1}" scope="page" />
						<tr>
						
							<td><c:out value="${data.plantID}"></c:out></td>
							<td><c:out value="${data.name}"></c:out></td>
							<td><img width="120px" src="${pageContext.request.contextPath}/${data.url}" alt=""/></td>
							<c:choose>
								<c:when test="${data.typeOfImageData=='N'}">
								<td>Native</td>
								</c:when>
							<c:otherwise>
									<td>Spreed</td>

								</c:otherwise>
							</c:choose>
							<td><c:out value="${data.order}"></c:out></td>
							<td><a
								href="${pageContext.request.contextPath}/imagedata/delete/${data.imageID}"><img
									src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">
				<c:if test="${!ulist.nav.firstPage}">
					<a href="${pageContext.request.contextPath}/imagedata/index">First</a>&nbsp;
			</c:if>
				<c:forEach var="i" items="${ulist.nav.indexList}">
					<c:choose>
						<c:when test="${i != ulist.nav.currentPage}">
							<a href="${pageContext.request.contextPath}/imagedata/index/${i}">${i}</a>&nbsp;
					</c:when>
						<c:otherwise>
							<b>${i}</b>&nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${!ulist.nav.lastPage}">
					<a
						href="${pageContext.request.contextPath}/imagedata/index/${ulist.nav.pageCount}">Last</a>
				</c:if>
			</div>
	</form:form>
</c:if>