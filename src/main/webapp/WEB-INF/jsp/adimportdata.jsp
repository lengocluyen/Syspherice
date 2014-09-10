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
		<c:if test="${!empty sheetdata}">
		$('#viewsheetdata').dataTable();
		</c:if>
		
	});
	function setUrl(url) {
		document.getElementById("path").value = url;
		$.colorbox.close();
	}
</script>
<c:if test="${!empty noticefail}">
<div class="alert alert-danger">
	<strong>Error Import data! <c:if test="${!empty message}">${message}</c:if></strong>
</div>
</c:if>
<c:if test="${!empty noticesuccess}">
<div class="alert alert-success">
	<strong>Well done!</strong> You successfully import to database with "${noticesuccess}".
</div>
</c:if>
<form:form class="custom-form" method="post"
	action='${pageContext.request.contextPath}/import/data'
	commandName="sheetinfo">
	<div class="row">
		<div class="col-md-6">
			<h4>Menu</h4>
			<!-- Nav tabs -->
			<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
				<li class="${tab1}"><a href="#section-1" data-toggle="tab">Import
						Data</a></li>
				<li class="${tab2}"><a href="#section-2" data-toggle="tab">List
						Sheets</a></li>
				<li class="${tab3}"><a href="#section-3" data-toggle="tab">Data Configuration</a></li>
			</ul>
			<div id="my-tab-content" class="tab-content">
				<div class="tab-pane fade ${tab1}" id="section-1">
					<div class="left60">
						<form:input path="path" />
					</div>
					<div class="button">
						<input id="iframe" class='mainBtn pull-right' type="button"
							value='Choose data file' />
					</div>
					<div class="button">
						<input id="importdata" name="checkdata"
							class='mainBtn pull-center' type="submit" value='Check Data' />
					</div>
					<div class="clear"></div>

				</div>
				<div class="tab-pane fade ${tab2}" id="section-2">
					<div class="input-select marginbottom10">
						<form:select path="number" class="postform">
							<form:option value="-1" label="--- Select ---" />
							<form:options items="${selectList}" itemValue="value"
								itemLabel="lable" />
						</form:select>
					</div>

					<div class="button">
						<input id="viewsheet" name="viewsheet" class='mainBtn pull-center'
							type="submit" value='View Sheet' />
					</div>
					<div class="clear"></div>
				</div>
				<div class="tab-pane fade ${tab3}" id="section-3">
					<div class="input-select marginbottom10">
						<label>Select a field for identifying</label>
						<form:select path="colonneID" class="postform">
							<!--<form:option value="-1" label="--- Select ---" />-->
							<form:options items="${colonnes}" itemValue="value"
								itemLabel="lable" />
						</form:select>
						<form:hidden path="name"/>
					</div>
					<div class="input-select marginbottom10">
						<label>Select column for making Index</label>
						<table class="table-data">
						<c:forEach items="${colonnes}" var="datacolonne">
							<tr><td><form:checkbox path="colonneIndex" value="${datacolonne.lable}"/></td><td><span style="padding:10px;margin-top:5px;">${datacolonne.lable}</span></td></tr>
						</c:forEach>
						</table>
					</div>
					<div class="button">
						<input id="selectidindex" name="selectidindex"
										class='mainBtn pull-center' type="submit" value='Import to Database' />
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<c:if test="${!empty colonnes}">
			<div class="col-md-6">
				<h4>Description</h4>
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne"> How to import data? </a>
						</div>
						<div id="collapseOne" class="panel-collapse collapse">
							<div class="panel-body">
								Use Guide
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>

	<hr />
	<c:if test="${!empty sheetdata}">
		<h4>View Sheet Data</h4>
	</c:if>
		<table id="viewsheetdata" class="table-data">
			<thead>
				<c:forEach items="${sheethead.colomns}" var="sheethead">
					<td><c:out value="${sheethead.cell}"></c:out></td>
				</c:forEach>
			</thead>
			<tbody>
				<c:forEach items="${sheetdata}" var="sheetdata">
					<tr>
						<c:forEach items="${sheetdata.colomns}" var="sheetdatacolumn">
							<td><c:out value="${sheetdatacolumn.cell}"></c:out></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</form:form>