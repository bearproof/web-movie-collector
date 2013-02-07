<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>

<link rel="stylesheet" href="/resources/common/css/reset.meyer-2.0.min.css" media="all" />
<link rel="stylesheet" href="/resources/common/css/base.css" media="all" />
<link rel="stylesheet" href="/resources/localeChanger/css/localeChanger.css" media="all" />
<link rel="stylesheet" href="/resources/common/css/easyui.css" media="all" />
<link rel="stylesheet" href="/resources/common/css/icon.css" media="all" />
<link href="/resources/jquery/jqueryUI-bootstrap/css/custom-theme/jquery-ui-1.10.0.custom.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/searchPage/css/searchPage.css" media="all" />
<link rel="stylesheet" href="/resources/css/css3treeview.css" media="all" />
<script type="text/javascript"
	src="/resources/js/css_browser_selector-4.0.min.js"></script>

<div id="WMCcontainer">

	<header id="header">
		<div>
			<h2 id="title"><spring:message code="searchPage.title" /></h2>
			
			<%@include file="/WEB-INF/views/localeChanger.jsp"%>
			
			<div class="btn-group">
				<a class="btn btn-inverse dropdown-toggle" data-toggle="dropdown"
					href="#"> Choose Template <span class="caret"></span>
				</a>
				
				<ul class="dropdown-menu">
					<li><a href="#" onClick="<c:set var='currentTemplate' value='Basic Template' />">Basic Template</a></li>
					<li><a href="#" onClick="<c:set var='currentTemplate' value='EZ Studios Template' />">EZ Studios Template</a></li>
				</ul>
			</div>
		</div>
	</header>
	
	<div id="mainContent" class="row-fluid">
	
		<div class="content">
			<div class="row-fluid">
				<header>
					<input type="text" class="movie-title" placeholder="<spring:message code="searchPage.movie.title.placeholder"/>" />
					<button class="btn btn-primary add"><spring:message code="searchPage.button.add" /></button>
				</header>
			</div>
			
			<div class="row-fluid divider">
				<c:forEach var="infoSource" items="${infoSources}" varStatus="status">
					<c:if test="${status.first}">
						<label class="info-source checkbox inline"><input
							type="checkbox" checked="checked" id="${infoSource}_site"
							value="${infoSource}" /> ${infoSource} </label>
					</c:if>
					
					<c:if test="${not status.first}">
						<label class="info-source checkbox inline"><input
							type="checkbox" id="${infoSource}_site" value="${infoSource}" />
							${infoSource} </label>
					</c:if>
				</c:forEach>
			</div>

			<div class="container-fluid">
				<div class="row-fluid">

					<div id="movieList" class="span3 css-treeview">
						<ul></ul>
					</div>

					<div class="span7">
						<ul id="movieTabHeader" class="nav nav-tabs"></ul>
						<div id="movieTabContent" class="tab-content"></div>
					</div>
				</div>
			</div>
			<%--end tag for center region layout --%>

			<!-- 		<div style="height: 40px;"> -->
			<footer class="main">
				<h5 class="page-footer"><%@include
						file="/WEB-INF/views/footer.jsp"%></h5>
			</footer>
			<!-- </div> -->
		</div>
		<!-- end of div#WMCcontainer -->

		<%-- MovieItemTemplates --%>
		<textarea id="searchItemTmpl" class="ui-helper-hidden">
			<li>
				<input id={searchTerm} type="checkbox" />
				<label for={searchTerm}>{movieTitle}</label>
				<ul class={searchTerm}></ul>
			</li>
		</textarea>
		
		<textarea id="movieDataSourceTmpl" class="ui-helper-hidden">
			<li>
				<input type="checkbox" id="{site}" />
				<label for={site}>{site}</label>
				<ul class={site}></ul>
			</li>	
		</textarea>
		
		<textarea id="movieItemTmpl" class="ui-helper-hidden">
			<li>
				<input type="checkbox" />
				<label for={title}>{title}</label>
				<ul>
					<li><span><spring:message code="searchPage.movie.year" />:&nbsp;{year}</span></li>
					<li><span><spring:message code="searchPage.movie.director" />:&nbsp;{director}</span></li>
					<li><a class="movie-id" href="#" data-uniqueId={uniqueid} title={uniqueid} data-site={site} data-siteid={siteid}><spring:message code="searchPage.movie.detaileddata" /></a></li>
				</ul>
			</li>
		</textarea>
		
		<textarea id="noMovieFoundTmpl" class="ui-helper-hidden">
			<li><span>{noMovieFound}</span></li>
		</textarea>

		<textarea id="detailedMovieItemTabHeader" class="ui-helper-hidden">
	 		<li><a data-toggle="tab" href="{movieId}">{movieTitle}&nbsp;<span class="closeTab">x</span></a></li>
		</textarea>

		<textarea id="detailedMovieItemTabContent" class="ui-helper-hidden">
			<div class="tab-pane" id={uniqueid}>			
				<ul>
					<li class="centered">
						<span class="bold"><spring:message code="searchPage.movie.site" />:&nbsp;</span>
						<span>{site}</span>
					</li>	
					
					<li>
						<ul>
							<li class="centered">{title}</li>
						</ul>
					</li>
					
					<li>
						<span class="bold"><spring:message code="searchPage.movie.year" /></span>
					
						<ul>
							<li>{year}</li>
						</ul>
					</li>
					
					<li>
						<span class="bold"><spring:message code="searchPage.movie.director" /></span>
					
						<ul>
							<li>{director}</li>
						</ul>
					</li>
					
					<li>
						<span class="bold"><spring:message code="searchPage.movie.description" /></span>
					
						<ul>
							<li>{description}</li>
						</ul>
					</li>
					
					<li><span class="bold">
						<spring:message code="searchPage.movie.cast" /></span>
					
						<ul>
							<li>{cast}</li>
						</ul>
					</li>
					
					<li>
						<span class="bold"><spring:message code="searchPage.movie.genre" /></span>
					
						<ul>
							<li>{genre}</li>
						</ul>
					</li>
					
					<li>
						<span class="bold"><spring:message code="searchPage.movie.rate" /></span>
					
						<ul>
							<li>{rate}</li>
						</ul>
					</li>
					
					<li>
						<span class="bold"><spring:message code="searchPage.movie.runtime" /></span>
					
						<ul>
							<li>{runtime}</li>
						</ul>
					</li>				
				</ul>
				
				<button class="btn btn-primary addToDB">
					<spring:message code="searchPage.movie.addToDB" />
				</button>
			</div>			
		</textarea>
		
		<%--Messages Component--%>
		<input type="hidden" class="messages"
			data-search-url='${pageContext.request.contextPath}/searchMovies/'
			data-detailedSearch-url='${pageContext.request.contextPath}/searchDetailedData/'
			data-saveMovie-url = '${pageContext.request.contextPath}/domain/movies/'
			data-searchPage.no.infosource.selected='<spring:message code="searchPage.no.infosource.selected"/>'
			data-searchpage.movie.required='<spring:message code="searchPage.movie.required"/>'
			data-searchPage.server.error='<spring:message code="searchPage.server.error"/>'
			data-searchPage.movie.not.found='<spring:message code="searchPage.movie.not.found"/>'
			data-searchPage.one.result.found='<spring:message code="searchPage.one.result.found"/>'>


		<script type="text/javascript" src="/resources/inheritance/js/inheritance-1.0.min.js"></script>
		<script type="text/javascript" src="/resources/jquery/jquery-1.8.3.min.js"></script>
		<!-- <script type="text/javascript" src="/resources/jquery/jqueryUI-bootstrap/js/jquery-ui-1.9.2.custom.min.js"></script> -->
		<script type="text/javascript" src="/resources/js/bootstrap-tab.js"></script>
		<script type="text/javascript" src="/resources/jquery/atmosphere/js/jquery.atmosphere-1.0.min.js"></script>
		<script type="text/javascript" src="/resources/common/js/base.js"></script>
		<script type="text/javascript" src="/resources/localeChanger/js/localeChanger.js"></script>
		<script type="text/javascript" src="/resources/searchPage/js/searchPage.js"></script>
	</div>
</div>
