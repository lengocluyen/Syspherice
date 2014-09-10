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
		action='${pageContext.request.contextPath}/searchtype/index'
		commandName="searchtype">
		<h3>List of Type de recherce</h3>
			<table class="table-data">
				<thead>
					<tr>
						<th>#</th>
						<th>Display Name</th>
						<th>Project Name</th>
						<th>Fields</th>
						<th>Create Date</th>
						<th>User import</th>
						<th>Desciption</th>
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
							<td><c:out value="${data.nameDisplay}"></c:out></td>
							<td><c:out value="${data.projectName}"></c:out></td>
							<td><a class="inline cboxElement"
							href='#${data.searchTypeID}'><c:out
									value="view"></c:out></a>
							<div style="display: none; margin: 15px;">
								<div id="${data.searchTypeID}"
									style="margin:0px; margin-bottom: 15px;">
									<table>
										<thead>
											<tr>
												<td>Field</td>
												<td>Collection</td>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${data.fieldOfCollections}" var="fcs">
											<tr>
												<td><c:out value="${fcs.field}"></c:out></td>
												<td><c:out value="${fcs.collection}"></c:out></td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div></td>
							<td><c:out value="${data.dateCreate}"></c:out></td>
							<td><c:out value="${data.userCreate}"></c:out></td>
							<td><c:out value="${data.description}"></c:out></td>
							<c:choose>
								<c:when test="${data.state=='Active'}">
									<td><a
										href="${pageContext.request.contextPath}/searchtype/update/${data.searchTypeID}">Active</a></td>
								</c:when>
								<c:otherwise>
									<td><a
										href="${pageContext.request.contextPath}/searchtype/update/${data.searchTypeID}">Unactive</a></td>

								</c:otherwise>
							</c:choose>
							<td><a
								href="${pageContext.request.contextPath}/searchtype/delete/${data.searchTypeID}"><img
									src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div align="center">
				<c:if test="${!ulist.nav.firstPage}">
					<a href="${pageContext.request.contextPath}/searchtype/index">First</a>&nbsp;
			</c:if>
				<c:forEach var="i" items="${ulist.nav.indexList}">
					<c:choose>
						<c:when test="${i != ulist.nav.currentPage}">
							<a href="${pageContext.request.contextPath}/searchtype/index/${i}">${i}</a>&nbsp;
					</c:when>
						<c:otherwise>
							<b>${i}</b>&nbsp;
					</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${!ulist.nav.lastPage}">
					<a
						href="${pageContext.request.contextPath}/searchtype/index/${ulist.nav.pageCount}">Last</a>
				</c:if>
			</div>
	</form:form>
</c:if>