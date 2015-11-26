<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.error {
	color: red;
}
</style>
<c:if test="${!empty noticefail}">
	<div class="alert alert-danger">
		<strong>Error adding a tags!</strong> <i>'${noticefail}'</i>
	</div>
</c:if>
<form:form class="import-form" method="post"
	action='${pageContext.request.contextPath}/tags/create'
	commandName="tags">


	<c:if test="${!empty noticesuccess}">
		<div class="alert alert-success">
			<strong>Well done!</strong> You successfully insert to database.

		</div>
	</c:if>


	<div class="row">
		<div class="import-page-content">
			<div class="import-heading">
				<h2 style="padding: 0px; margin: 0px;">Add a new tag</h2>
				<p>For adding a new tag. We have to fill up
					informations in following form</p>
			</div>
			<div class="import-form clearfix">
				<p class="full-row">
					<span class="import-label"> <label for="name">Name: &nbsp; <form:errors path="name" cssClass="error" />
					</label> <span class="small-text">Put tags name here</span>
					</span>
					<form:input path="name" />
				</p>
				<p class="full-row">
					<span class="import-label"> <label for="description">Description:</label>
						<span class="small-text">Type description of yourself</span>
					</span>
					<form:textarea path="description" row="25" />
				</p>
				<p class="full-row">
					<input class="mainBtn" type="submit" value="Submit">
				</p>
			</div>
		</div>
	</div>
</form:form>