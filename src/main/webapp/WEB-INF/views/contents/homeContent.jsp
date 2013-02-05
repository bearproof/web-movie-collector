<div class="hero-unit">
	<h2>Hello, ${userContext.userFullName}!</h2>
	<p>Accounts manager is a simple web application that do basic operations (create,read,update and delete) on 2 entities: Account and Role.The purpose of this application is to put in practice some nice technologies as:</p>
	<ul>
		<li>Spring MVC with Rest like url for controllers.</li>
		<li>Spring Security for user/password authentication.</li>
		<li>Spring Data JPA for persistency.</li>
		<li>Twitter BootStrap for the UI.</li>
		<li>Jquery for Ajax for REST calls.</li>
		<li>JQuery DataDatable plugin.</li>
		<li>Apache Tiles for web page templating.</li>
	</ul>
	
</div>
<div class="page-header">
  <h1>Application pages:</h1>
</div>
<div id="myCarousel" class="carousel slide">
                <div class="carousel-inner">
                  <div class="item active">
                    <img src="${pageContext.request.contextPath}/resources/app-screenshots/Home.png" alt="">
                    <div class="carousel-caption">
                      <h4>Home Page</h4>
                      <p>The application home page</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="${pageContext.request.contextPath}/resources/app-screenshots/Accounts-List.png" alt="">
                    <div class="carousel-caption">
                      <h4>The accounts page</h4>
                      <p>List account with pagination and sorting support.</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="${pageContext.request.contextPath}/resources/app-screenshots/Account-Create.png" alt="">
                    <div class="carousel-caption">
                      <h4>Create account operation</h4>
                      <p>Create account page with client side validation.</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="${pageContext.request.contextPath}/resources/app-screenshots/Account-Update.png" alt="">
                    <div class="carousel-caption">
                      <h4>Update account operation</h4>
                      <p>Update account page with client side validation.</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="${pageContext.request.contextPath}/resources/app-screenshots/Account-Delete.png" alt="">
                    <div class="carousel-caption">
                      <h4>Delete account operation</h4>
                    </div>
                  </div>
                </div>
                <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
</div>