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
		document.getElementById("fileUrl").value = url;
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
	action='${pageContext.request.contextPath}/exceldatadoc/create'
	commandName="docexcel">
<c:if test="${!empty noticesuccess}">
	<div class="alert alert-success">
		<strong>Well done!</strong> You successfully import to database.
		
	</div>
	<div class="row" style="margin-bottom:15px;">
		<input class="mainBtn" type="submit" style="margin-left:10px;"  name="sbimport" id="sbimport" value="Choose et Import Data">
		<input class="mainBtn" type="submit" style="margin-left:20px;"  name="sblist" id="sblist" value="List Excel Document">
	</div>
</c:if>

	
	<div class="row">
		<div class="import-page-content">
			<div class="import-heading">
				<h2 style="padding:0px;margin:0px;">Insert a project information with data from a file Excel</h2>
				<p>To import data in file excel to database. We have to fill up
					informations in following form</p>
			</div>
			<div class="import-form clearfix">
				<p class="full-row">
					<span class="import-label"> <label for="projectname">Project
							name:</label> <span class="small-text">Put Project Name here</span>
					</span>
					<form:input path="projectname" />&nbsp;&nbsp;&nbsp;<form:errors path="projectname" cssClass="error"/>
				</p>
				<p class="full-row">
					<span class="import-label"> <label for="dateImport">Import
							Date:</label> <span class="small-text">Put Import Date here</span>
					</span>
					<form:input path="dateImport" />
				</p>
				<p class="full-row">
					<span class="import-label"> <label for="fileUrl">Url
							File Excel:</label> <span class="small-text">Choose data file</span>
					</span>
					<form:input path="fileUrl" />&nbsp;&nbsp;&nbsp;<form:errors path="fileUrl" cssClass="error"/>
					<input id="iframe" class='mainBtn' style="margin-left:10px;" type="button"
						value='Choose data file' />
				</p>
				<p class="full-row">
					<span class="import-label"> <label for="description">Description:</label>
						<span class="small-text">Type Description</span>
					</span>
					<form:textarea path="description" rows="8"/>
				</p>
				<p class="full-row">
					<input class="mainBtn" type="submit" name="sbcreateinfo" id="sbcreateinfo"  value="Submit">
				</p>
			</div>
		</div>
	</div>
</form:form>