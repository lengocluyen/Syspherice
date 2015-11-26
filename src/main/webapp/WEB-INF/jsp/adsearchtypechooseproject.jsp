<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8">
	jQuery(document).ready(function($) {
		document.getElementById("fileAvatar").onchange = function() {
			document.getElementById("avatar").value = this.value;
		};

		$(".datepicker").datepicker({
			dateFormat : 'mm-dd-yy'
		});
	});
</script>
<style>
.error {
	color: red;
}
</style>
<c:if test="${!empty noticefail}">
	<div class="alert alert-danger">
		<strong>Error adding a type of search!</strong> <i>'${noticefail}'</i>
	</div>
</c:if>
<form:form class="import-form" method="post"
	action='${pageContext.request.contextPath}/searchtype/chooseproject'
	commandName="searchtype">


	<c:if test="${!empty noticesuccess}">
		<div class="alert alert-success">
			<strong>Well done!</strong> You successfully insert to database.

		</div>
	</c:if>


	<div class="row">
		<div class="import-page-content">
			<div class="import-heading">
				<h2 style="padding: 0px; margin: 0px;">Add new type of search</h2>
				<p>For adding new type of search. We have to fill up
					informations in following form</p>
			</div>
			<div class="import-form clearfix">
				<p class="full-row input-select2">
					<span class="import-label"> <label for="prjectName">Project
							Name:</label> <span class="small-text">Choose project name here</span>
					</span>
					<form:select path="projectName" class="postform">
						<form:option value="-1" label="--- Select ---" />
						<form:options items="${projects}" itemValue="projectname"
							itemLabel="projectname" />
					</form:select>
				</p>
				<p class="full-row">
					<input class="mainBtn" type="submit" value="Submit">
				</p>
			</div>
		</div>
	</div>
</form:form>
<!-- /.widget-inner -->
