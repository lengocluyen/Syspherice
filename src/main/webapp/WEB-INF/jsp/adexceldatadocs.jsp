<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8">
	$().ready(function() {
		$(".inline").colorbox({inline:true,width:"30%"});
	
		<c:if test="${!empty exceldatadoclist}">
		$('#viewdata').dataTable();
		</c:if>
		
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
		action='${pageContext.request.contextPath}/exceldatadoc/index'
		commandName="exceldatadoc">
		<h3 style="margin-top: 5px;">List of excel data and project
			information</h3>
		<table class="table-data">
			<thead>
				<tr>
					<th>#</th>
					<th>Project Name</th>
					<!-- <th>File Data</th> -->
					<th>Metadata</th>
					<th>Date Import</th>
					<th>User Import</th>
					<th>Description</th>
					<th>Import Data</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="count" value="0" scope="page" />
				<c:forEach items="${ulist.entities}" var="data">
					<c:set var="count" value="${count + 1}" scope="page" />
					<tr>
						<td><c:out value="${count}"></c:out></td>
						<td><c:out value="${data.projectname}"></c:out></td>
						<!-- <td><c:out value="${data.fileUrl}"></c:out></td> -->
						<td><a class="inline cboxElement"
							href='#${data.textDataDocID}'><c:out
									value="${data.metadata.author}"></c:out></a>
							<div style="display: none; margin: 15px;">
								<div id="${data.textDataDocID}"
									style="margin-top: 0px;">
									<table>
										<thead>
											<tr>
												<td colspan="2"><h3>Metadata Information</h3></td>
											</tr>
											<tr>
												<td>Link</td>
												<td><a
													href="${pageContext.request.contextPath}${data.fileUrl}">
														Download</a></td>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>Title</td>
												<td><c:out value="${data.metadata.author}"></c:out></td>
											</tr>
											<tr>
												<td>Subject</td>
												<td><c:out value="${data.metadata.subjet}"></c:out></td>
											</tr>
											<tr>
												<td>Author</td>
												<td><c:out value="${data.metadata.author}"></c:out></td>
											</tr>
											<tr>
												<td>Comments</td>
												<td><c:out value="${data.metadata.comments}"></c:out></td>
											</tr>
											<tr>
												<td>Keywords</td>
												<td><c:out value="${data.metadata.keywords}"></c:out></td>
											</tr>
											<tr>
												<td>Create DateTime</td>
												<td><c:out value="${data.metadata.createDateTime}"></c:out></td>
											</tr>
											<tr>
												<td>Last Save DateTime</td>
												<td><c:out value="${data.metadata.lastSaveDateTime}"></c:out></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div></td>
						<td><c:out value="${data.dateImport}"></c:out></td>
						<td><c:out value="${data.userImport}"></c:out></td>
						<td><c:out value="${data.description}"></c:out></td>
						<td><a
							href="${pageContext.request.contextPath}/import/dataid/${data.textDataDocID}"><img
								src="${pageContext.request.contextPath}/resources/images/import.png" /></a></td>
						<td><a
							href="${pageContext.request.contextPath}/exceldatadoc/update/${data.textDataDocID}"><img
								src="${pageContext.request.contextPath}/resources/images/edit.png" /></a></td>
						<td><a
							href="${pageContext.request.contextPath}/exceldatadoc/delete/${data.textDataDocID}"><img
								src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div align="center">
			<c:if test="${!ulist.nav.firstPage}">
				<a href="${pageContext.request.contextPath}/exceldatadoc/index">First</a>&nbsp;
			</c:if>
			<c:forEach var="i" items="${ulist.nav.indexList}">
				<c:choose>
					<c:when test="${i != ulist.nav.currentPage}">
						<a
							href="${pageContext.request.contextPath}/exceldatadoc/index/${i}">${i}</a>&nbsp;
					</c:when>
					<c:otherwise>
						<b>${i}</b>&nbsp;
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${!ulist.nav.lastPage}">
				<a
					href="${pageContext.request.contextPath}/exceldatadoc/index/${ulist.nav.pageCount}">Last</a>
			</c:if>
		</div>
	</form:form>
</c:if>