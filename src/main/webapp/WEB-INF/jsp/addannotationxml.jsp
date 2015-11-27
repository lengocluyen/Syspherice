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
						<div class="col-md-4 mix students mix_all">


							<form:form method="post"
								action='${pageContext.request.contextPath}/annotation/add/${annotation.objectID}'
								commandName="annotation">


								<div class="row">
									<div class="import-page-content">
										<div class="import-heading">
											<h3
												style="border-bottom: 1px solid; border-color: #d5dbe0; padding-bottom: 8px;">Xml
												Annotation</h3>

											<p>For more information of xml file. Share your annotation
												of this file...</p>
										</div>
										<div class="clearfix">
											<c:if test="${!empty noticesuccess}">
												<div class="alert alert-success">
													<strong>Well done!</strong> You successfully send annotation.

												</div>
											</c:if>
											<c:if test="${!empty noticefail}">
												<div class="alert alert-danger">
													<strong>Send unsuccessful annotation!</strong>
												</div>
											</c:if>
											<p class="full-row">
												<textarea name="content" id="content"
													style="width: 100%; border-color: #d5dbe0" rows="15"></textarea>
											</p>
											<p class="full-row">
											<center>
												<input class="mainBtn" type="submit" name=""
													value="Send Annotations">
											</center>
											</p>
										</div>
									</div>
								</div>
							</form:form>
						</div>

						<div class="col-md-8 mix students mix_all" data-cat="3"
							style="display: inline-block; opacity: 1;">

							<div class="gallery-item" style="margin-top: 15px;">
								<a target="_blank" class="fancybox" rel="gallery1"
									href="${pageContext.request.contextPath}/${imageobject.urlTemps}">
									<div class="gallery-thumb" style="height: 60%;width:60%;">
										<img
											src="${pageContext.request.contextPath}/resources/images/xml.png"
											alt="">
									</div>
									<div class="gallery-content">
										<p class="small-text" style="text-align: center;">Sowing
											number: ${imageobject.sowing_nb}
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp; Repet number:
											${imageobject.repet_nb}
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp; Plant number:
											${imageobject.plant_nb}
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Panicle
											number: ${imageobject.panicle_nb}</p>
									</div>
								</a>
							</div>
						</div>

					</div>
				</div>

				<!-- /.widget-inner -->
			</div>
			<!-- /.widget-main -->

		</div>
		<!-- /.col-md-12 -->
		<c:if test="${!empty annotationinfo}">
			<div class="col-md-12">
				<div id="blog-comments" class="blog-post-comments">
					<div class="widget-main-title">
						<h4 class="widget-title">Annotations List</h4>
					</div>
					<div class="blog-comments-content">
					<c:if test="${!empty noticesuccess1}">
												<div class="alert alert-success">
													<strong>Well done!</strong> Deleted your annotation!.

												</div>
											</c:if>
											<c:if test="${!empty noticefail1}">
												<div class="alert alert-danger">
													<strong>Error!</strong>
												</div>
											</c:if>
						<c:forEach items="${annotationinfo}" var="result">
							<div class="media">
								<div class="pull-left" href="#">
									<img class="media-object"
										src="${result.avatar}"
										alt="">
								</div>
								<div class="media-body">
									<h4 class="media-heading">${result.fullname}</h4>
									<p>
										${result.content} - ${result.createdate}
										<c:if test="${result.author}">
											<span style="text-align: right;"><a
												href="${pageContext.request.contextPath}/annotation/del/${result.annotationid}"><img
													src="${pageContext.request.contextPath}/resources/images/delete.png" /></a></span>
										</c:if>
									</p>

								</div>
							</div>
						</c:forEach>
						<!--					<div class="media">
						<div class="pull-left" href="#">
							<img class="media-object" src="images/blog/comment-author3.jpg"
								alt="">
						</div>
						<div class="media-body">
							<h4 class="media-heading">Fred Guerra</h4>
							<p>Enim, iste numquam inventore reiciendis magnam aliquam
								libero asperiores quidem quos voluptate?</p>
						</div>
					</div>-->
					</div>
					<!-- /.blog-comments-content -->
				</div>

			</div>
		</c:if>
	</div>
	<!-- /.row -->
</div>
<!-- /.container -->