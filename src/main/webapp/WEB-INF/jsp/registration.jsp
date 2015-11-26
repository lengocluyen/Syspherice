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
<div class="container" style="min-height: 100%">
	<div class="row">
		<div class="col-md-12">

			<div class="widget-main">
				<div class="widget-inner shortcode-typo">
					<c:if test="${!empty noticefail}">
						<div class="alert alert-danger">
							<strong>Error registrer a news account!</strong> <i>'${noticefail}'</i>
						</div>
					</c:if>
					<c:if test="${isactionupdate=='false'}">
						<form:form class="import-form" method="post"
							action='${pageContext.request.contextPath}/registration'
							commandName="account" enctype="multipart/form-data">


							<c:if test="${!empty noticesuccess}">
								<div class="alert alert-success">
									<strong>Well done!</strong> You successfully registrer to
									database.

								</div>
							</c:if>


							<div class="row">
								<div class="import-page-content">
									<div class="import-heading">
										<h2 style="padding: 0px; margin: 0px;">
											Registration of new account
										
										</h2>
										<p>To sign up a new account. We have to fill up
											informations in following form</p>
									</div>
									<div class="import-form clearfix">
										<p class="full-row">
											<span class="import-label"> <label for="firstName">First
													Name: &nbsp; <form:errors path="firstName" cssClass="error" />
											</label> <span class="small-text">Put your first name here</span>
											</span>
											<form:input path="firstName" />
										</p>
										<p class="full-row">
											<span class="import-label"> <label for="lastName">First
													Name:&nbsp; <form:errors path="lastName" cssClass="error" />
											</label> <span class="small-text">Put your last name here</span>
											</span>
											<form:input path="lastName" />
										</p>
											<p class="full-row">
												<span class="import-label"> <label for="username">Username:&nbsp;
														<form:errors path="username" cssClass="error" />
												</label> <span class="small-text">Put your user name here</span>
												</span>
												<form:input path="username" />
											</p>
										<p class="full-row">
											<span class="import-label"> <label for="password">Password:&nbsp;
													<form:errors path="password" cssClass="error" />
											</label> <span class="small-text">Put your password here</span>
											</span>
											<form:input path="password" type="password" />
										</p>

										<p class="full-row">
											<span class="import-label"> <label for="email">Email:&nbsp;
													<form:errors path="email" cssClass="error" />
											</label> <span class="small-text">Put your email here</span>
											</span>
											<form:input path="email" />

										</p>

										<p class="full-row input-select2">
											<span class="import-label"> <label for="sex">Sex:</label>
												<span class="small-text">Choose your sex here</span>
											</span>
											<form:select path="sex">
												<form:option value="Male" label="Male" />
												<form:option value="Female" label="Female" />
											</form:select>
										</p>

										<p class="full-row">
											<span class="import-label"> <label for="birthdate">Birth
													Date:&nbsp; <form:errors path="birthdate" cssClass="error" />
											</label> <span class="small-text">Put your birth date here</span>
											</span>
											<form:input path="birthdate" class="datepicker" />

										</p>
										<p class="full-row">
											<span class="import-label"> <label for="fileUrl">Avatar:</label>
												<span class="small-text">Choose your image</span>
											</span> <input id="avatar" placeholder="Choose File"
												disabled="disabled" /> <span
												class="fileUpload btn btn-default"
												style="margin-bottom: 10px;"> <span>Upload</span> <input
												name="fileAvatar" id="fileAvatar" type="file" class="upload" />
											</span>
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
					</c:if>


					<c:if test="${isactionupdate=='true'}">
						<form:form class="import-form" method="post"
							action='${pageContext.request.contextPath}/accountdetail/update/${account.username}'
							commandName="account" enctype="multipart/form-data">


							<c:if test="${!empty noticesuccess}">
								<div class="alert alert-success">
									<strong>Well done!</strong> You successfully registrer to
									database.

								</div>
							</c:if>


							<div class="row">
								<div class="import-page-content">
									<div class="import-heading">
										<h2 style="padding: 0px; margin: 0px;">
											Update account

										</h2>
										<p>To update your account. We have to fill up
											informations in following form</p>
									</div>
									<div class="import-form clearfix">
										<p class="full-row">
											<span class="import-label"> <label for="firstName">First
													Name: &nbsp; <form:errors path="firstName" cssClass="error" />
											</label> <span class="small-text">Put your first name here</span>
											</span>
											<form:input path="firstName" />
											&nbsp;
											<form:errors path="firstName" cssClass="error" />
										</p>
										<p class="full-row">
											<span class="import-label"> <label for="lastName">First
													Name:&nbsp; <form:errors path="lastName" cssClass="error" />
											</label> <span class="small-text">Put your last name here</span>
											</span>
											<form:input path="lastName" />
										</p>
											<p class="full-row">
												<span class="import-label"> <label for="username">Username:&nbsp;
														<form:errors path="username" cssClass="error" />
												</label> <span class="small-text">Put your user name here</span>
												</span>
												<form:input readonly="true" path="username" />
											</p>
										<p class="full-row">
											<span class="import-label"> <label for="password">Password:&nbsp;
													<form:errors path="password" cssClass="error" />
											</label> <span class="small-text">Put your password here</span>
											</span>
											<form:input path="password" type="password" />
										</p>

										<p class="full-row">
											<span class="import-label"> <label for="email">Email:&nbsp;
													<form:errors path="email" cssClass="error" />
											</label> <span class="small-text">Put your email here</span>
											</span>
											<form:input path="email" />

										</p>

										<p class="full-row input-select2">
											<span class="import-label"> <label for="sex">Sex:</label>
												<span class="small-text">Choose your sex here</span>
											</span>
											<form:select path="sex">
												<form:option value="Male" label="Male" />
												<form:option value="Female" label="Female" />
											</form:select>
										</p>

										<p class="full-row">
											<span class="import-label"> <label for="birthdate">Birth
													Date:&nbsp; <form:errors path="birthdate" cssClass="error" />
											</label> <span class="small-text">Put your birth date here</span>
											</span>
											<form:input path="birthdate" class="datepicker" />

										</p>
										<p class="full-row">
											<span class="import-label"> <label for="fileUrl">Avatar:</label>
												<span class="small-text">Choose your image</span>
											</span> <input id="avatar" placeholder="Choose File"
												disabled="disabled" /> <span class="fileUpload btn btn-default"
												style="margin-bottom: 10px;"> <span>Upload</span> 
												<input name="fileAvatar" id="fileAvatar" type="file" class="upload" />
											</span>
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
					</c:if>

					<!-- /.widget-inner -->
				</div>
				<!-- /.widget-main -->

			</div>
			<!-- /.col-md-12 -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
</div>