<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<c:if test="${!empty message}">
				<div class="alert alert-information">
					<strong>${message}</strong>
				</div>
			</c:if>
			<div class="widget-main">
				<div class="widget-inner shortcode-typo">
					<div class="course-search">
						<h3 style="margin-bottom: 0px;">Search Page</h3>
						<form:form id="quick_form" style="margin-top:0px;"
							class="course-search-form clearfix" method="post"
							action='${pageContext.request.contextPath}/search/result'
							commandName="searchinfo">
							<div id="search_advanced" class="clearfix">
								<p class="search-form-item">
									<label class="alabel" for="Campus">&nbsp;</label>
									<form:input path="searchContent" />
								</p>
								<p class="search-form-item">
									<label class="alabel" for="cat">Search data of project:</label>
									<form:select path="projectType" class="postform">
										<form:option value="-1" label="--- Select ---" />
										<form:options items="${projects}" itemValue="textDataDocID"
											itemLabel="projectname" />
									</form:select>
								</p>
								<p class="search-form-item">
									<label class="alabel" for="cat">Choose a data type:</label>
									<form:select path="searchType" class="postform">
										<form:option value="-1" label="--- Select ---" />
									</form:select>
								</p>
								<script type="text/javascript">
									$(document)
											.ready(
													function() {
														var textDataDocID = $(
																"#projectType option:selected")
																.val();
														$
																.getJSON(
																		"${pageContext.request.contextPath}/search/searchtypes/"
																				+ textDataDocID,
																		function(
																				data) {
																			var $selectSearchType = $("#searchType");
																			$selectSearchType
																					.empty();
																			$
																					.each(
																							data,
																							function(
																									i,
																									item) {
																								$selectSearchType
																										.append("<option value="+item.searchTypeID+">"
																												+ item.nameDisplay
																												+ "</option");
																							})
																		})
														$("#projectType")
																.change(
																		function() {
																			var textDataDocID = $(
																					"#projectType option:selected")
																					.val();
																			$
																					.getJSON(
																							"${pageContext.request.contextPath}/search/searchtypes/"
																									+ textDataDocID,
																							function(
																									data) {
																								var $selectSearchType = $("#searchType");
																								$selectSearchType
																										.empty();
																								$
																										.each(
																												data,
																												function(
																														i,
																														item) {
																													$selectSearchType
																															.append("<option value="+item.searchTypeID+">"
																																	+ item.nameDisplay
																																	+ "</option");
																												})
																							})
																		});
													});
								</script>
								<p class="search-form-item-button">
									<label class="alabel" for="Campus">&nbsp;</label> <input
										class="mainBtn pull-right" type="submit" name=""
										value="Search" style="padding: 0px;">
								</p>
							</div>
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
<div class="container">
	<div class="row">
		<div class="col-md-12">

			<div class="widget-main">
				<div class="widget-inner shortcode-typo">
					<div class="course-search" style="min-height: 408px">
						<h3 style="margin-bottom: 0px;padding-bottom:15px;"><strong>Results</strong></h3>
						<form:form id="quick_form" class="course-search-form"
							style="margin-top:0px;" method="post"
							action='${pageContext.request.contextPath}/search/result'
							commandName="contact">
							<script>
								$(document).ready(
										function() {
											$(".titlecollection").each(
													function() {
														var title = $(this)
																.text()
																.replace(/\_/g,
																		' - ');
														$(this).html(title);
													});
										});
							</script>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${searchresult}" var="result">
								<h3>
									Collection: <span class="titlecollection">${result.key }</span>
								</h3>
								<div style="overflow: auto;">
									<c:if test="${!empty result.value}">
									<c:set var="count" value="${count + 1}" scope="page" />
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
																<c:if
																	test="${data.key!='_id'&&data.key!='textDataDocID'}">
																	<td><c:out value="${data.value}"></c:out></td>
																</c:if>
															</c:forEach>
															<td><a
																href='${pageContext.request.contextPath}/search/viewdetail?collectionid=${uObject.data._id}&collectionname=${result.key}'>View</a></td>
														</tr>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty result.value}">
									<div class="col-md-12">
										<div class="alert alert-information">
											<strong>No data found!</strong>
										</div>
										</div>
									</c:if>
								</div>
							</c:forEach>
							<!-- Export  -->
							<c:if test="${count > 0}">
							<c:if test="${!empty sessionScope.Account}">
								<center>
									<input class="mainBtn" style="margin-left: 0px"
										style="align:center;" type="button" name=""
										value="Export this results to excel"
										onclick="window.open('${pageContext.request.contextPath}/search/exporttoexcel');">
									<input class="mainBtn" style="margin-left: 0px"
										style="align:center;" type="button" name=""
										value="Export this resuls to zip file"
										onclick="window.location.href='${pageContext.request.contextPath}/search/exporttozip';">
								</center>
							</c:if>
							</c:if>
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