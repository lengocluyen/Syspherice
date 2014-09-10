<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="request-information">
	<h4 class="widget-title">Search Information</h4>
	<form:form class="request-info clearfix" method="post"
		action='${pageContext.request.contextPath}/search/result'
		commandName="searchinfo">
	<div class="full-row">
		<form:input path="searchContent"/>
	</div>
	<div class="full-row">
		<label for="cat">Search data of project:</label>
		<div class="input-select">
			<form:select path="projectType" class="postform">
				<form:option value="-1" label="--- Select ---" />
				<form:options items="${projects}" itemValue="textDataDocID" itemLabel="projectname" />
			</form:select>
		</div>
		<!-- /.input-select -->
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		$("#projectType").change(function(){
			
			var textDataDocID =$("#projectType option:selected").val();
			$.getJSON("${pageContext.request.contextPath}/search/searchtypes/"+textDataDocID,function(data){
				var $selectSearchType = $("#searchType");
				$selectSearchType.empty();
				$.each(data,function(i,item){
					$selectSearchType.append("<option value="+item.searchTypeID+">"+item.nameDisplay+"</option");
				})
			})
		});
	});
	</script>
	<div class="full-row">
		<label for="cat">Choose a data type:</label>
		<div class="input-select">
			<form:select path="searchType" class="postform">
				<form:option value="-1" label="--- Select ---" />
			</form:select>
		</div>
		<!-- /.input-select -->
	</div>
	<!-- /.full-row -->
	<div class="full-row">
		<div class="submit_field">
			<!--<span class="small-text pull-left">Subscribe to our newsletter</span>  -->
			<input class="mainBtn pull-right" type="submit" name=""
				value="Search" style="padding:0px;" />
				
		</div>
		<!-- /.submit-field -->
	</div>
	<!-- /.full-row -->


	</form:form>
	<!-- /.request-info -->
</div>
<!-- /.request-information -->
