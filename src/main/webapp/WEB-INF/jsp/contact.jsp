<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.error {
	color: red;
}
</style>
<div class="container" style="min-height: 100%">
	<div class="row">
		<div class="col-md-12">

			<div class="widget-main">
				<div class="widget-inner shortcode-typo">
					<c:if test="${!empty noticefail}">
						<div class="alert alert-danger">
							<strong>Send unsuccessful contact!</strong>
						</div>
					</c:if>

					<form:form class="import-form" method="post"
						action='${pageContext.request.contextPath}/contactpage'
						commandName="contact">
						<c:if test="${!empty noticesuccess}">
							<div class="alert alert-success">
								<strong>Well done!</strong> You successfully send contact.

							</div>
						</c:if>


						<div class="row">
							<div class="import-page-content">
								<div class="import-heading">
									<h3>Our Contact Details</h3>
									<p>Someone finds it difficult to understand your creative
										idea? There is a better visualisation. Share your views with
										me...</p>
								</div>
								<div class="contact-form clearfix">
									<p class="full-row">
										<span class="import-label"> <label for="name-id">First
												Name:&nbsp; <form:errors path="firstName" cssClass="error" />
										</label> <span class="small-text">Put your name here</span>
										</span>
										<form:input path="firstName" />
									</p>
									<p class="full-row">
										<span class="import-label"> <label for="lastName">Last
												Name:&nbsp; <form:errors path="lastName" cssClass="error" />
										</label> <span class="small-text">Put your surname here</span>
										</span>
										<form:input path="lastName" />
									</p>
									<p class="full-row">
										<span class="import-label"> <label for="email">E-mail:&nbsp;
												<form:errors path="email" cssClass="error" />
										</label> <span class="small-text">Type email address</span>
										</span>
										<form:input path="email" />
									</p>
									<p class="full-row">
										<span class="import-label"> <label for="message">Message:&nbsp;
												<form:errors path="message" cssClass="error" />
										</label> <span class="small-text">Type email address</span>
										</span>
										<textarea name="message" id="message" rows="6"></textarea>
									</p>
									<p class="full-row">
										<input class="mainBtn" type="submit" name=""
											value="Send Message">
									</p>
								</div>
							</div>
						</div>
					</form:form>
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
