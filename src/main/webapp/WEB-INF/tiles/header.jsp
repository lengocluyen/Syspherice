<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container">
	<div class="row">
		<div class="col-md-8 header-left">
			<div class="logo">Rice phenotype database</div>
			<!-- /.logo -->
		</div>
		<!-- /.header-left -->

		<div class="col-md-4"></div>

		<div class="col-md-4 header-right">
			<ul class="small-links">
				<!-- /.col-md-4 -->
				<c:choose>
					<c:when test="${!empty sessionScope.Account}">
						<li><a style="color: white">${sessionScope.Account.firstName}
								${sessionScope.Account.lastName}</a></li>
						<c:if test="${sessionScope.IsAdmin=='true'}">
							<c:choose>
								<c:when test="${sessionScope.IsAdminPage=='false'}">
									<li><a href="${pageContext.request.contextPath}/admin/">Admin
											page</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}">User
											page</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
						<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
					</c:when>
					<c:otherwise>
						<li><a class="inlinelogin" href='#loginpage'>Login</a></li>
						<li><a href="${pageContext.request.contextPath}/registration">Registration</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="search-form">
				<form:form class="search_form" name="search_form" method="post"
					action='${pageContext.request.contextPath}/search'
					commandName="searchInfo">
					<input type="text" name="s" placeholder="Search the site..."
						title="Search the site..." class="field_search" />
				</form:form>
			</div>

		</div>
	</div>
</div>
<!-- /.container -->
<script>
	$().ready(function() {
		$(".inlinelogin").colorbox({
			inline : true,
			width : "35%"
			<c:if test="${!empty loginreload}">
			, open:true
			</c:if>
					
		});
	});
</script>
<div style="display: none">

	<div id="loginpage" style="margin: 20px; height: 230px;"
		class="request-information">
		<form:form class="request-info clearfix" method="post"
			action='${pageContext.request.contextPath}/login'
			commandName="account">

			<h4 class="widget-title">Login Form</h4>
			<c:if test="${!empty noticefail}">
				<div class="alert alert-danger">
					<i>${noticefail} </i>
				</div>
			</c:if>
			<div class="full-row">
				<label for="yourname">Username:</label> <input type="text"
					id="username" name="username" />
			</div>
			<div class="full-row">
				<label for="password">Password:</label> <input type="password"
					id="password" name="password" />
			</div>

			<div>
				<div class="submit_field">

					<input style="width: 50%; padding: 0px" class="mainBtn pull-right"
						type="button"
						onclick="location.href='${pageContext.request.contextPath}/registration'"
						name="" value="Sign up" style="padding: 0px;" /> <input
						style="width: 50%; padding: 0px;" class="mainBtn pull-right"
						type="submit" name="" value="Sign in" style="padding: 0px;" />

				</div>
				<!-- /.submit-field -->
			</div>
		</form:form>
	</div>
</div>
