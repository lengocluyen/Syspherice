<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="col-md-12">
		<div class="widget-main">
			<div class="widget-main-title">
				<h4 class="widget-title">Tags</h4>
			</div>
			<!-- /.widget-main-title -->
			<div class="widget-inner">
				<div class="tag-items">
					<c:forEach items="${tags}" var="data">
						<a href="${pageContext.request.contextPath}/search/tags/${data.tagID}" rel="tag">${data.name}</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- /.widget-main -->
	</div>
	<!-- /.col-md-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-md-12">
		<div class="widget-main">
			<div class="widget-main-title">
				<h4 class="widget-title">Partners</h4>
			</div>
			<!-- /.widget-main-title -->
			<div class="widget-inner">
				<div class="our-campus clearfix">
					<ul>
						<li><img height="85px" style="padding-left: 10px"
							src="./resources/images/campus/umon.png" alt="" /></li>
						<li><img height="85px" style="padding-left: 10px"
							src="./resources/images/campus/agi.png" alt="" /></li>
						<li><img height="85px" style="padding-left: 10px"
							src="./resources/images/campus/ird.png" alt="" /></li>
						<li><img height="85px" style="padding-left: 10px"
							src="./resources/images/campus/vaas.png" alt="" /></li>
						<li><img height="85px"
							src="./resources/images/campus/usth.png" alt="" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- /.widget-main -->
	</div>
	<!-- /.col-md-12 -->
</div>
<!-- /.row -->