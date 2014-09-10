<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8">
	$().ready(function() {
		$("#iframe").click(function() {

			$.colorbox({
				width : "60%",
				height : "66%",
				iframe : true,
				href : '<c:url value="/admin/importfinder" />'
			});

		});

	});
	function setUrl(url) {
		document.getElementById("url").value = url;
		$.colorbox.close();
	}
</script>
<style>
.error {
	color: red;
}
</style>
<c:if test="${!empty noticefail} ">
	<div class="alert alert-danger">
		<strong>Error Import data! <c:if test="${!empty message} ">${message}</c:if></strong>
	</div>
</c:if>

<form:form class="import-form" method="post"
	action='${pageContext.request.contextPath}/imagedata/import'
	commandName="folderimage">
	<c:if test="${!empty noticesuccess}">
		<div class="alert alert-success">
			<strong>Well done!</strong> You successfully import to database.
		</div>
	</c:if>


	<div class="row">
		<div class="import-page-content">
			<div class="import-heading">
				<h2 style="padding: 0px; margin: 0px;">Import image of project</h2>
				<p>To import image data to database. We have to fill up
					informations in following form</p>
			</div>
			<div class="import-form clearfix">
				<p class="full-row  input-select2">
					<span class="import-label"> <label for="projectname">Project
							name:&nbsp;<form:errors path="projectname" cssClass="error" /></label> <span class="small-text">Choose project Name here</span>
					</span>
					<form:select path="projectname" class="postform">
						<form:option value="-1" label="--- Select ---" />
						<form:options items="${projects}" itemValue="textDataDocID"
							itemLabel="projectname" />
					</form:select>
				</p>
				<p class="full-row">
					<span class="import-label"> <label for="url">Chooose folder:</label> 
					<span class="small-text">Choose folder image</span>
					</span>
					<form:input path="url" />&nbsp;&nbsp;&nbsp;<form:errors path="url" cssClass="error"/>
					<input id="iframe" class='mainBtn' style="margin-left:10px;" type="button"
						value='Choose folder' />
				</p>
				<p class="full-row">
					<span class="import-label"> <label for="dateImport">Import
							Date:</label> <span class="small-text">Put Import Date here</span>
					</span>
					<form:input path="dateImport"  readonly="true" />
				</p>
				<p class="full-row">
					<input class="mainBtn" type="submit" name="sbcreateinfo"
						id="sbcreateinfo" value="Submit">
				</p>
			</div>
		</div>
	</div>
</form:form>