<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>File Manager</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/smoothness/jquery-ui.css">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value='/resources/elfinder/css/elfinder.min.css' />">
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value='/resources/elfinder/css/theme.css' />">

<script type="text/javascript"
	src="<c:url value='/resources/elfinder/js/elfinder.js' />"></script>
<script type="text/javascript" charset="utf-8">
	$().ready(function() {
		var elf = $('#finder').elfinder({
			url : '<c:url value="/connector" />',
			getFileCallback : function(file) {
				window.parent.setUrl(file);
				top.window.close();
			},
			resizable : false
		}).elfinder('instance');
	});
</script>
</head>
<body>
	<div id="finder"></div>

</body>
</html>
