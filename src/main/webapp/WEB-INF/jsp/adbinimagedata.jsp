<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

		<div class="col-md-12" style="padding-bottom:0px;">
			<c:if test="${!empty ulist.entities}">
				<form:form class="custom-form" method="post"
					action='${pageContext.request.contextPath}/binimagedata'
					commandName="imagedata">
						<div class="widget-main-title" style="padding-top:0px;padding-bottom:0px;">
							<h3>Image Data List</h3>
						</div>
						<div class="blog-comments-content">
							<c:if test="${!empty noticefail}">
								<div class="alert alert-danger">
									<strong>Oop!</strong> Failed implementation
								</div>
							</c:if>
							<c:if test="${!empty noticesuccess}">
								<div class="alert alert-success">
									<strong>Well done!</strong> Successful implementation
								</div>
							</c:if>
							<c:forEach items="${ulist.entities}" var="data">
								<div class="col-md-4 mix students mix_all" data-cat="3"
									style="display: inline-block; opacity: 1;">

									<div class="gallery-item" style="margin-top: 15px;">
										<a class="fancybox" rel="gallery1"
											href="${pageContext.request.contextPath}/${data.urlTemps}">
											<div class="gallery-thumb">
												<img src="${pageContext.request.contextPath}/${data.urlTemps}"
													alt="">
											</div>
											<div style="text-align: center;" class="gallery-content">
												<h3>
													<c:out value="${data.name}"></c:out>
												</h3>
												<p class="small-text">Sowing number: ${data.sowing_nb}
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Repet number:
													${data.repet_nb}</p>
												<p class="small-text">Plant number: ${data.plant_nb}
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Panicle
													number: ${data.panicle_nb}</p>

											</div>
										</a>
										<div style="float: left; width: 45%">
											<input class="mainBtn" type="button"
												style="width: 100%; align: center;" value="Image annotation"
												onclick="window.location.href='${pageContext.request.contextPath}/annotation/add/${data.imageID}';">
										</div>
										<div style="float: right; width: 45%;">
											<input class="mainBtn" type="button"
												style="width: 100%; align: center;" value="Delete"
												onclick="window.location.href='${pageContext.request.contextPath}/binimagedata/delete/${data.imageID}';">
										</div>
										<div style="clear: both;"></div>
									</div>
								</div>

							</c:forEach>
							<div style="clear:both;"></div>
						</div>
					<div class="clear:both;" align="center">
						<c:if test="${!ulist.nav.firstPage}">
							<a href="${pageContext.request.contextPath}/binimagedata/index">First</a>&nbsp;
						</c:if>
						<c:forEach var="i" items="${ulist.nav.indexList}">
							<c:choose>
								<c:when test="${i != ulist.nav.currentPage}">
									<a
										href="${pageContext.request.contextPath}/binimagedata/index/${i}">${i}</a>&nbsp;
					</c:when>
								<c:otherwise>
									<b>${i}</b>&nbsp;
					</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${!ulist.nav.lastPage}">
							<a
								href="${pageContext.request.contextPath}/binimagedata/index/${ulist.nav.pageCount}">Last</a>
						</c:if>
					</div>
				</form:form>
			</c:if>
		</div>