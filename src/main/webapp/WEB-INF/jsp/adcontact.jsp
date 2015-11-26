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
		action='${pageContext.request.contextPath}/contact'
		commandName="contact">
		<h3>Contact List</h3>
			<table class="table-data">
				<thead>
					<tr>
						<th>Ordre</th>
						<th>Name</th>
						<th>Email</th>
						<th>Message</th>
						<th>IsRead</th>
						<th>Date send</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${ulist.entities}" var="data">
						<c:set var="count" value="${count + 1}" scope="page" />
						<tr>
							<td><c:out value="${count}"></c:out></td>
							<td><c:out value="${data.firstName} ${data.lastName}"></c:out></td>
							<td><c:out value="${data.email}"></c:out></td>
							<td><c:out value="${data.message}"></c:out></td>
							<c:choose>
								<c:when test="${data.isRead=='true'}">
									<td><a
										href="${pageContext.request.contextPath}/contact/update/${data.contactID}">Read</a></td>
								</c:when>
								<c:otherwise>
									<td><a
										href="${pageContext.request.contextPath}/contact/update/${data.contactID}">Unread</a></td>

								</c:otherwise>
							</c:choose>
							<td><c:out value="${data.dateSend}"></c:out></td>
							<td><a
								href="${pageContext.request.contextPath}/contact/delete/${data.contactID}"><img
									src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">
				<c:if test="${!ulist.nav.firstPage}">
					<a href="${pageContext.request.contextPath}/contact/index">First</a>&nbsp;
			</c:if>
				<c:forEach var="i" items="${ulist.nav.indexList}">
					<c:choose>
						<c:when test="${i != ulist.nav.currentPage}">
							<a href="${pageContext.request.contextPath}/contact/index/${i}">${i}</a>&nbsp;
					</c:when>
						<c:otherwise>
							<b>${i}</b>&nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${!ulist.nav.lastPage}">
					<a
						href="${pageContext.request.contextPath}/contact/index/${ulist.nav.pageCount}">Last</a>
				</c:if>
			</div>
	</form:form>
</c:if>