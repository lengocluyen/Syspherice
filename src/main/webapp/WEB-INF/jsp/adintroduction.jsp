<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src='<c:url value="/resources/ckeditor/ckeditor.js"/>'></script>
<script src='<c:url value="/resources/ckeditor/adapters/jquery.js"/>'></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('textarea#contentData').ckeditor();
	});
</script>
<style>
.error {
	color: red;
}
</style>
<div style="margin: 20px;">


	<form:form class="import-form" method="post"
		action='${pageContext.request.contextPath}/admin/introduction'
		commandName="introdata">
		<c:if test="${!empty noticesuccess}">
			<div class="alert alert-success">
				<strong>Well done!</strong> You successfully update to database.
			</div>
		</c:if>
		<c:if test="${!empty noticefail} ">
			<div class="alert alert-danger">
				<strong>Failed update data! </strong>
			</div>
		</c:if>

		<div class="row">
			<div class="import-page-content">
				<div class="import-heading">
					<h2 style="padding: 0px; margin: 0px;">Modify the introduction
						of project</h2>
					<p>To update the introduction of project. We have to fill up
						informations in following form</p>
				</div>
				<div class="import-form clearfix">
					<p class="full-row">
						<form:textarea path="contentData" rows="40" />
					</p>
					<center>
						<input class="mainBtn" style="margin-left: 0px"
							style="align:center;" type="submit" name="" value="Submit">
					</center>
				</div>
			</div>
		</div>
	</form:form>
</div>
<div style="clear: both;"></div>