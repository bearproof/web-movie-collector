<%@include file="/WEB-INF/views/includes/taglibs.jsp"%>
<div id="WMCcontainer">	
	<div id="mainContent" class="row-fluid">
		<div class="userInputZone">
		<header id="header">
			<div>
				<h2 id="title">
					<spring:message code="searchPage.title" />
				</h2>		
			</div>
		</header>
			<header>
				<input type="text" class="movie-title"
					placeholder="<spring:message code="searchPage.movie.title.placeholder"/>" />
				<button class="btn btn-primary add">
					<spring:message code="searchPage.button.add" />
				</button>
			</header>

			<div class="divider">
				<div class="infoSourceCheckboxes">
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
			</div>
		</div>

		<div class="content">
			<div class="container-fluid">
				<div class="row-fluid">
					<div id="movieList" class="span4 css-treeview">
						<ul></ul>
					</div>

					<div class="span8">
						<ul id="movieTabHeader" class="nav nav-tabs"></ul>
						<div id="movieTabContent" class="tab-content"></div>
					</div>
				</div>
			</div> 
		</div><%--end tag for center region layout --%>


<%-- MovieItemTemplates --%>
<textarea id="searchItemTmpl" class="ui-helper-hidden">
	<li>
		<input id={searchTerm} type="checkbox">
		<label class="searchTermTreeIcon" for={searchTerm}>{movieTitle}&nbsp;<span class="removeTreeNode">x</span></label>
		<ul class={searchTerm}></ul>
	</li>
</textarea>

<textarea id="movieDataSourceTmpl" class="ui-helper-hidden">
	<li>
		<input type="checkbox" id="{movieandsite}">
		<label class="infosourceTreeIcon" for={movieandsite}>{site}</label>
		<ul class={movieandsite}></ul>
	</li>	
</textarea>

<textarea id="movieItemTmpl" class="ui-helper-hidden">
	<li>
		<input type="checkbox" id={uniqueid}>
		<label for={uniqueid}>{title}</label>
		<ul>
			<li><span><spring:message code="searchPage.movie.year" />:&nbsp;{year}</span></li>
			<li><span><spring:message code="searchPage.movie.director" />:&nbsp;{director}</span></li>
			<li>
				<a class="movie-id" href="#" data-uniqueId={uniqueid} title={uniqueid} data-site={site} data-siteid={siteid}>
				<spring:message	code="searchPage.movie.detaileddata" />
				</a>
			</li>
		</ul>
	</li>
</textarea>

<textarea id="noMovieFoundTmpl" class="ui-helper-hidden">
	<li>
		<input type="checkbox" id="{movieandsite}">
		<label class="infosourceTreeIcon" for={movieandsite}>{site}</label>
		<ul class={movieandsite}><li><span><spring:message code="searchPage.movie.not.found" /></span></li></ul>
	</li>		
</textarea>

<textarea id="oneMovieFoundTmpl" class="ui-helper-hidden">
	<li>
		<input type="checkbox" id="{movieandsite}">
		<label class="infosourceTreeIcon" for={movieandsite}>{site}</label>
		<ul class={movieandsite}><li><span><spring:message code="searchPage.one.result.found" /></span></li></ul>
	</li>		
</textarea>

<textarea id="detailedMovieItemTabHeader" class="ui-helper-hidden">
		<li><a data-toggle="tab" href="{uniqueid}">{movieTitle}&nbsp;<span class="closeTab">x</span></a></li>
</textarea>

<textarea id="detailedMovieItemTabContent" class="ui-helper-hidden">
	<div class="tab-pane" id={uniqueid}>			
		<ul>
			<li class="centered website-label-container">
				<span class="bold"><spring:message
						code="searchPage.movie.site" />:&nbsp;</span>
				<span class="movieSite">{site}</span>
			</li>	
			
			<li>
				<div class="title-container">
					<span class="title">{title}</span>
				</div>
				<div class="submit-container">
					<button class="btn btn-primary addToDB">
						<spring:message code="searchPage.movie.addToDB" />
					</button>
				</div>	
			</li>		
		</ul>
	
		<table summary="This table shows the details for the selected movie" class="movieDetailsTable">
			<tr>
				<th><spring:message code="searchPage.movie.year" />:</th>
				<td class="year">{year}</td>
			</tr>
			<tr>
				<th><spring:message code="searchPage.movie.director" />:</th>
				<td class="director">{director}</td>
			</tr>
			<tr>
				<th><spring:message code="searchPage.movie.description" />:</th>
				<td class="description">{description}</td>
			</tr>
			<tr>
				<th><spring:message code="searchPage.movie.cast" />:</th>
				<td class="cast">{cast}</td>
			</tr>
			<tr>
				<th><spring:message code="searchPage.movie.genre" />:</th>
				<td class="genre">{genre}</td>
			</tr>
			<tr>
				<th><spring:message code="searchPage.movie.rate" />:</th>
				<td class="rate">{rate}</td>
			</tr>
			<tr>
				<th><spring:message code="searchPage.movie.runtime" />:</th>
				<td class="runtime">{runtime}</td>
			</tr>
			<tr>
				<th><spring:message code="domain.movie.column.userRating" />:</th>
				<td>
					<select class="userRating">
						<option value="0" selected="selected"><spring:message code="domain.movie.column.userRating.unrated" /></option>
						<option value="1"><spring:message code="domain.movie.column.userRating.dontbother" /></option>
						<option value="2"><spring:message code="domain.movie.column.userRating.prettygood" /></option>
						<option value="3"><spring:message code="domain.movie.column.userRating.verygood" /></option>
						<option value="4"><spring:message code="domain.movie.column.userRating.excellent" /></option>
					</select> 					
				</td>
			</tr>
			<tr>
				<th><spring:message code="domain.movie.column.movieStatus" />:</th>
				<td>
					<select class="movieStatus">
						<option value="0" selected="selected"><spring:message code="domain.movie.column.movieStatus.notspecified" /></option>												
						<option value="1"><spring:message code="domain.movie.column.movieStatus.dontwannasee" /></option>	
						<option value="2" ><spring:message code="domain.movie.column.movieStatus.wannasee" /></option>
						<option value="3"><spring:message code="domain.movie.column.movieStatus.seenalready" /></option>
					</select> 
				</td>
			</tr>
			<tr>
				<th><spring:message code="domain.movie.column.shelfLocation" />:</th>
				<td>
					<input type="text" class="shelfLocation" value="" placeholder="Shelf Location">
				</td>	
			</tr>
			<tr>
				<th><spring:message code="domain.movie.column.lentTo" />:</th>
				<td>
					<input type="text" class="lentTo" value="" placeholder="Lent To">
				</td>	
			</tr>						
			<tr>
				<th><spring:message code="domain.movie.column.ownMovieNotes" />:</th>
				<td>
					&lt;textarea class="ownMovieNotes" maxlength="300" placeholder="My Movie Review"&gt;&lt;/textarea&gt;
				</td>					
			</tr>		
			<tr>
				<th class="submit-blank-label"></th>
				<td class="submit-container">
					<button class="btn btn-primary addToDB">
						<spring:message code="searchPage.movie.addToDB" />
					</button>
				</td>
			</tr>				
		</table>	
	</div>			
</textarea>

<%--Messages Component--%>
<input type="hidden" class="messages"
	data-search-url='${pageContext.request.contextPath}/searchMovies/'
	data-detailedSearch-url='${pageContext.request.contextPath}/searchDetailedData/'
	data-saveMovie-url='${pageContext.request.contextPath}/domain/movies/'
	data-searchPage.no.infosource.selected='<spring:message code="searchPage.no.infosource.selected"/>'
	data-searchpage.movie.required='<spring:message code="searchPage.movie.required"/>'
	data-searchPage.server.error='<spring:message code="searchPage.server.error"/>'
	data-searchPage.movie.not.found='<spring:message code="searchPage.movie.not.found"/>'
	data-searchPage.one.result.found='<spring:message code="searchPage.one.result.found"/>'>	
	</div>
</div><!-- end of div#WMCcontainer -->
