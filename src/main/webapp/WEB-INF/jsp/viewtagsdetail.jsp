<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container">
	<div class="row">
		<div class="col-md-12">

			<div class="widget-main">
				<div class="widget-inner shortcode-typo">
					<div class="course-search" style="min-height: 408px">
						<h3 style="margin-bottom: 0px;">Results</h3>
						<form:form id="quick_form" class="course-search-form"
							style="margin-top:0px;" >
							<script>
							$(document).ready(function(){
								$(".titlecollection").each(function(){
									var title = $(this).text().replace(/\_/g, ' - ');
									$(this).html(title);
								});
							});
							</script>
							<c:forEach items="${searchresult}" var="result">
								<h3>Collection: <span  class="titlecollection">${result.key }</span></h3>
								<table class="table-data">
									<thead>
										<tr>
											<c:forEach items="${result.value[0].data}" var="data">
												<c:if test="${data.key!='_id'&&data.key!='textDataDocID'}">
												<td><c:out value="${data.key}"></c:out></td>
												</c:if>
											</c:forEach>
											<td>Images</td>
										</tr>
									</thead>
									<tbody>
									<c:set var="count" value="0" scope="page" />
										
										<c:forEach items="${result.value}" var="uObject">
										<c:if test="${count<10}">
											<c:set var="count" value="${count + 1}" scope="page" />
											<tr>
												<c:forEach items="${uObject.data}" var="data">
												<c:if test="${data.key!='_id'&&data.key!='textDataDocID'}">
													<td><c:out value="${data.value}"></c:out></td>
													</c:if>
												</c:forEach>
												<td><a href='${pageContext.request.contextPath}/search/viewdetail?collectionid=${uObject.data._id}&collectionname=${result.key}'>View</a></td>
											</tr>
											</c:if>
										</c:forEach>
									</tbody>
								</table>
							</c:forEach>
						</form:form>
					</div>
				</div>
				<!-- /.widget-inner -->
			</div>
			<!-- /.widget-main -->

		</div>
		<!-- /.col-md-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /.container -->