<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <div class="nav-bar-main" role="navigation">
            <div class="container">
                <nav class="main-navigation clearfix visible-md visible-lg" role="navigation">
                        <ul class="main-menu sf-menu">
                        	<c:forEach items="${menu}" var="menu">
						<li><a href="${menu.url}" class="${menu.isCurrent}"> ${menu.name} </a></li>
					</c:forEach>
                        </ul> <!-- /.main-menu -->

                        <ul class="social-icons pull-right">
                            <li><a href="#" data-toggle="tooltip" title="Facebook"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="#" data-toggle="tooltip" title="Twitter"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#" data-toggle="tooltip" title="Pinterest"><i class="fa fa-pinterest"></i></a></li>
                            <li><a href="#" data-toggle="tooltip" title="Google+"><i class="fa fa-google-plus"></i></a></li>
                            <li><a href="#" data-toggle="tooltip" title="RSS"><i class="fa fa-rss"></i></a></li>
                        </ul> <!-- /.social-icons -->
                </nav> <!-- /.main-navigation -->
            </div> <!-- /.container -->
        </div> <!-- /.nav-bar-main -->
       