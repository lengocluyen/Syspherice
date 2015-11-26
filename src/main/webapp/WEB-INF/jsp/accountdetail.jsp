<div class="container" style="min-height: 100%">
	<div class="row">
		<div class="col-md-12">

			<div class="widget-main">
				<div class="widget-inner shortcode-typo">
<h3 style="margin-top:0px;padding-left:20px;;padding-top:15px;">Account Detail Information</h3>
					<div class="event-container clearfix">
						<div class="left-event-content">
							<img width="300px" src="${account.avatar}" alt=""/>
							<div class="event-contact">
								<h4>Account Details</h4>
								<ul>
									<li>Username: ${account.username}</li>
									<li>Email: ${account.email}</li>
									<li>Sex: ${account.sex}</li>
									<li>BirthDate: ${account.birthdate}</li>
								</ul>
							</div>
							<div class="view-details"><a href="${pageContext.request.contextPath}/registration/${account.username}" class="lightBtn">Update</a></div>
						</div>
						<!-- /.left-event-content -->
						<div class="right-event-content">
							<h2 class="event-title">${account.firstName}
								${account.lastName}</h2>
							<span class="event-time">Date Create:
								${account.dateCreate} - Last login: ${account.lastLogin}</span>
							<p>
								<strong class="dark-text">Description:</strong>
								${account.description}
							</p>
						</div>
						<!-- /.right-event-content -->
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
</div>