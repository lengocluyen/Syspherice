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
		action='${pageContext.request.contextPath}/account'
		commandName="account">
		<h3>Account List</h3>
			<table class="table-data">
				<thead>
					<tr>
						<th>Ordre</th>
						<th>Name</th>
						<th>Email</th>
						<th>Username</th>
						<!-- <th>Birthday</th>-->
						<th>Sex</th>
						<!--<th>Date Create</th>-->
						 <th>Last Login</th>
						<th>State</th>
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
							<td><c:out value="${data.username}"></c:out></td>
							<!-- <td><c:out value="${data.birthdate}"></c:out></td> -->
							<td><c:out value="${data.sex}"></c:out></td>
							<!-- <td><c:out value="${data.dateCreate}"></c:out></td> -->
							<td><c:out value="${data.lastLogin}"></c:out></td>
							<c:choose>
								<c:when test="${data.state=='active'}">
									<td><a
										href="${pageContext.request.contextPath}/account/update/${data.username}">Active</a></td>
								</c:when>
							<c:otherwise>
									<td><a
										href="${pageContext.request.contextPath}/account/update/${data.username}">Unactive</a></td>

								</c:otherwise>
							</c:choose>
							<td><a
								href="${pageContext.request.contextPath}/account/delete/${data.username}"><img
									src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">
				<c:if test="${!ulist.nav.firstPage}">
					<a href="${pageContext.request.contextPath}/account/index">First</a>&nbsp;
			</c:if>
				<c:forEach var="i" items="${ulist.nav.indexList}">
					<c:choose>
						<c:when test="${i != ulist.nav.currentPage}">
							<a href="${pageContext.request.contextPath}/account/index/${i}">${i}</a>&nbsp;
					</c:when>
						<c:otherwise>
							<b>${i}</b>&nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${!ulist.nav.lastPage}">
					<a
						href="${pageContext.request.contextPath}/account/index/${ulist.nav.pageCount}">Last</a>
				</c:if>
			</div>
	</form:form>
</c:if>