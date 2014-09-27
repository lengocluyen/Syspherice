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
					<div dir="ltr" style="text-align:justify;padding:0 30px 20px 30px;">
						${data}
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
