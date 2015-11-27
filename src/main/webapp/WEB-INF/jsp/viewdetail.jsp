<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.error {
	color: red;
}
</style>
<div class="container" style="min-height: 100%">
	<div class="row" style="min-height: 464px;">
		<div class="col-md-12">

			<div class="widget-main">
				<div class="widget-inner shortcode-typo">

					<div class="row">
						<div class="col-md-12">
							<h4>View details</h4>
							<!-- Nav tabs -->
							<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
								<li class=" ${active1}"><a href="#section-1"
									data-toggle="tab">Data</a></li>
								<li class=" ${active2}"><a href="#section-2"
									data-toggle="tab">Images</a></li>
								<c:if test="${!empty sessionScope.Account}">
									<li class=" ${active3}"><a href="#section-3"
										data-toggle="tab">Tagging</a></li>
								</c:if>
							</ul>
						</div>
						<div id="my-tab-content" class="tab-content">
							<div class="tab-pane fade in ${active1}" id="section-1">
								<c:forEach items="${uobject}" var="result">
									<h3>${result.key }</h3>
									<div style="overflow: auto;">
										<table class="table-data">
											<thead>
												<tr>
													<c:forEach items="${result.value[0].data}" var="data">
														<c:if test="${data.key!='_id'&&data.key!='textDataDocID'}">
															<td><c:out value="${data.key}"></c:out></td>
														</c:if>
													</c:forEach>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${result.value}" var="uObject">
													<tr>
														<c:forEach items="${uObject.data}" var="data">
															<c:if
																test="${data.key!='_id'&&data.key!='textDataDocID'}">
																<td><c:out value="${data.value}"></c:out></td>
															</c:if>
														</c:forEach>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:forEach>
							</div>
							<div class="tab-pane fade in ${active2}" id="section-2"   style="margin-top:0px;padding-top:3px;">
								<div class="row"  style="margin-top:0px;">
									<div class="gallery-small-thumbs clearfix"  style="margin-top:0px;">
										<div id="Grid" style="margin-top:0px;margin-left:10px;">
											<c:forEach items="${imagedata}" var="data">
												<div class="col-md-4 mix students mix_all" data-cat="3"
													style="display: inline-block; opacity: 1;">

													<div class="gallery-item" style="margin-top:15px;">
														<a class="fancybox" rel="gallery1"
															href="${pageContext.request.contextPath}/${data.urlTemps}">
															<div class="gallery-thumb">
																<img
																	src="${pageContext.request.contextPath}/${data.urlTemps}"
																	alt="">
															</div>
															<div class="gallery-content">
																<p class="small-text">
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sowing number: ${data.sowing_nb} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Repet number: ${data.repet_nb}</p>
																<p class="small-text"> 
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Plant number: ${data.plant_nb} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Panicle number: ${data.panicle_nb}</p>											
															</div>
														</a>
														<input class="mainBtn" type="button" style="width:100%;align:center;"
															value="Image annotation"
										onclick="window.location.href='${pageContext.request.contextPath}/annotation/add/${data.imageID}';">
													</div>
												</div>

											</c:forEach>

										</div>
									</div>
								</div>

							</div>
							<c:if test="${!empty sessionScope.Account}">

								<div class="tab-pane fade in ${active3}" id="section-3">

									<c:if test="${!empty noticefail}">
										<div class="alert alert-danger">
											<strong>Error adding a type of search!</strong> <i>'${noticefail}'</i>
										</div>
									</c:if>
									<form:form class="import-form" method="post"
										action='${pageContext.request.contextPath}/search/additemtag'
										commandName="itemtags">


										<c:if test="${!empty noticesuccess}">
											<div class="alert alert-success">
												<strong>Well done!</strong> You successfully insert to
												database.

											</div>
										</c:if>


										<div class="row">
											<div class="import-page-content">
												<div class="import-heading">
													<h2 style="padding: 0px; margin: 0px;">Choose a item
														tag</h2>
													<p>For adding new item tag. We have to fill up
														informations in following form</p>
												</div>
												<div class="import-form clearfix">
													<p class="full-row input-select2">
														<span class="import-label"> <label for="tagID">
																Tage Name:</label> <span class="small-text">Choose tag
																name here</span>
														</span>
														<form:select path="tagID" class="postform">
															<form:option value="-1" label="--- Select ---" />
															<form:options items="${tags}" itemValue="tagID"
																itemLabel="name" />
														</form:select>
													</p>
													<p class="full-row">
														<input class="mainBtn" type="submit" value="Submit">
													</p>
												</div>
											</div>
										</div>
									</form:form>
								</div>
							</c:if>
						</div>
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