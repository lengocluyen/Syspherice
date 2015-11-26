<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> 
</html><![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="en"> 
</html><![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="en"> </html><![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->
<head>
<title></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 

<!-- CSS Bootstrap & Custom -->
<link href='<c:url value="/resources/bootstrap/css/bootstrap.css"/>'
	rel="stylesheet" media="screen" />
<link href='<c:url value="/resources/css/font-awesome.min.css"/>'
	rel="stylesheet" media="screen" />
<link href='<c:url value="/resources/css/animate.css"/>'
	rel="stylesheet" media="screen" />
<link href='<c:url value="/resources/css/style.css"/>'
	rel="stylesheet" media="screen" />

<!-- Favicons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href='<c:url value="/resources/images/ico/apple-touch-icon-144-precomposed.png"/>' />
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href='<c:url value="/resources/images/ico/apple-touch-icon-114-precomposed.png"/>' />
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href='<c:url value="/resources/images/ico/apple-touch-icon-72-precomposed.png"/>' />
<link rel="apple-touch-icon-precomposed"
	href='<c:url value="/resources/images/ico/apple-touch-icon-57-precomposed.png"/>' />
	
<link rel="shortcut icon" href="./resources/images/ico/favicon.ico" />

<!-- JavaScripts -->
<script src='<c:url value="/resources/js/jquery-1.10.2.min.js"/>'></script>
<script src='<c:url value="/resources/js/jquery-migrate-1.2.1.min.js"/>'></script>
<script src='<c:url value="/resources/js/modernizr.js"/>'>
<!--[if lt IE 8]>
	<div style=' clear: both; text-align:center; position: relative;'> < a
	href = "http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode" > <img src="../../../../storage.ie6countdown.com/assets/100/images/banners/warning_bar_0000_us.jpg" border="0" alt="" /></a>
	</div> < ![ endif ]-->
</script>

<script src='<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>'></script>
<script src='<c:url value="/resources/js/plugins.js"/>'></script>
<script src='<c:url value="/resources/js/custom.js"/>'></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value='/resources/css/colorbox.css'/>">
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value='/resources/media/css/jquery.dataTables.css'/>">
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.colorbox.js' />"></script>
<!--<script type="text/javascript"
	src="<c:url value='/resources/media/js/jquery.js' />"></script>-->
<script type="text/javascript"
	src="<c:url value='/resources/media/js/jquery.dataTables.js' />"></script>

</head>
<body>
	<!-- for devices -->
	<tiles:insertAttribute name="menudevices" />

	<header class="site-header"> <tiles:insertAttribute
		name="header" /> <tiles:insertAttribute name="menu" /> </header>
	<!-- /.site-header -->


	<div class="container">
		<div class="row">
			

			<div class="col-md-5">
				<div class="widget-item">
					<tiles:insertAttribute name="boxinfo" />
				</div>
				<!-- /.widget-item -->
			</div>
			<!-- /.col-md-4 -->
			<div class="col-md-7">
				<tiles:insertAttribute name="slideshow" />
			</div>
			<!-- /.col-md-12 -->
		</div>
	</div>


	<div class="container">
		<div class="row">

			<!-- Here begin Main Content -->
			<div class="col-md-8">

				<tiles:insertAttribute name="body" />
			</div>
			<!-- /.col-md-8 -->

			<!-- Here begin Sidebar -->
			<div class="col-md-4">

				<!-- Professseur -->
				<tiles:insertAttribute name="leftbox" />
				<!-- Our gallery -->
				<tiles:insertAttribute name="gallery" />
			</div>
		</div>
	</div>

	<!-- begin The Footer -->
	<footer class="site-footer"> <tiles:insertAttribute
		name="footer" /> </footer>
	<!-- /.site-footer -->


</body>
</html>