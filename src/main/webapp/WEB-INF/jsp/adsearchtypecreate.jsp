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
	action='${pageContext.request.contextPath}/searchtype/create'
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
				<p class="full-row">
					<span class="import-label"> <label for="nameDisplay">Name
							Display: &nbsp; <form:errors path="nameDisplay" cssClass="error" />
					</label> <span class="small-text">Put your display name here</span>
					</span>
					<form:input path="nameDisplay" />
				</p>
				<p class="full-row input-select2">
					<span class="import-label"> <label for="prjectName">Project
							Name:</label> <span class="small-text">Choose project name here</span>
					</span>
					<form:input path="projectName" readonly="true" />
				</p>

				<div class="full-row input-select2">
					<span class="import-label"> <label for="fieldOfCollections">Field
							of Collection:&nbsp; <form:errors path="fieldOfCollections"
								cssClass="error" />
					</label> <span class="small-text">Choose field search here</span>
					</span>
						<c:set var="index" value="0" scope="page" />
						<c:forEach items="${fieldcollectiondatas}" var="fcdata">
							<div style="margin-left:20%">
							<div style="float:left;width:25%"><form:select
										path="fieldOfCollections[${index}].field"
										class="postform" style="width:100%">
										<form:option value="-1">--- Select ---</form:option>
										<c:forEach items="${fcdata.value}" var="fcvalue">
											<form:option value="${fcvalue}">${fcvalue}</form:option>
										</c:forEach>
									</form:select></div>
								<div style="float:left;width:50%;padding-left:10px;">
								
									<form:label path="fieldOfCollections[${index}].collection">${fcdata.key}</form:label>
								<input id="fieldOfCollections[${index}].collection" name="fieldOfCollections[${index}].collection" type="hidden" value="${fcdata.key}"/>
								</div>
							</div><div  style="clear:both">
							</div>
							<c:set var="index" value="${index + 1}" scope="page" />
						</c:forEach>
				</div>

				<p class="full-row">
					<span class="import-label"> <label for="dateCreate">Date
							Create:</label> <span class="small-text">Date Create here</span>
					</span>
					<form:input path="dateCreate" readonly="true" />
				</p>


				<p class="full-row input-select2">
					<span class="import-label"> <label for="state">State:</label>
						<span class="small-text">Choose your state here</span>
					</span>
					<form:select path="state">
						<form:option value="Active" label="Active" />
						<form:option value="Unactive" label="Unactive" />
					</form:select>
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