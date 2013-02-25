<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="formsContent">
	<!-- Form container -->
	<form id="movieEditForm" class="form-horizontal" autocomplete="off" method="POST" action="">
	<legend><fmt:message key="domain.movie.edit.legend"/></legend>
		<div class="control-group">
			<label class="control-label" for="id"><fmt:message key="domain.movie.column.id"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="id" name="id" readOnly="true" value="${crudObj.id}" maxlength="100" data-reset="${crudObj.id}">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="title"><fmt:message key="domain.movie.column.title"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="title" name="title" value="${crudObj.title}" maxlength="100" placeholder="Movie Title" data-reset="${crudObj.title}">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="year"><fmt:message key="domain.movie.column.year"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="year" name="year" value="${crudObj.year}" maxlength="100" placeholder="Year" data-reset="${crudObj.year}">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="genre"><fmt:message key="domain.movie.column.genre"/></label>
			<div class="controls">
				<input type="text" id="genre" name="genre" value="${crudObj.genre}" maxlength="100" placeholder="The genre of the movie" data-reset="${crudObj.genre}">
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="director"><fmt:message key="domain.movie.column.director"/></label>
			<div class="controls">
				<input type="text" id="director" name="director" value="${crudObj.director}" maxlength="100" placeholder="The director" data-reset="${crudObj.director}">
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="cast"><fmt:message key="domain.movie.column.cast"/></label>
			<div class="controls">
				<textarea id="cast" name="cast" maxlength="200" placeholder="<fmt:message key="domain.movie.column.cast"/>">${crudObj.cast}</textarea>
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="description"><fmt:message key="domain.movie.column.description"/></label>
			<div class="controls">
				<textarea id="description" name="description" maxlength="500" placeholder="Description">${crudObj.description}</textarea>
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="runtime"><fmt:message key="domain.movie.column.runtime"/></label>
			<div class="controls">
				<input type="text" id="runtime" name="runtime" value="${crudObj.runtime}" maxlength="100" placeholder="Runtime (min)" data-reset="${crudObj.runtime}">
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="userRating"><fmt:message key="domain.movie.column.userRating"/></label>
			<div class="controls">				
				<select id="userRating" class="userRating">
					<option value="0" <c:if test="${crudObj.userRating=='0'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.userRating.unrated"/></option>
					<option value="1" <c:if test="${crudObj.userRating=='1'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.userRating.dontbother"/></option>
					<option value="2" <c:if test="${crudObj.userRating=='2'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.userRating.prettygood"/></option>
					<option value="3" <c:if test="${crudObj.userRating=='3'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.userRating.verygood"/></option>
					<option value="4" <c:if test="${crudObj.userRating=='4'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.userRating.excellent"/></option>
				</select> 
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="movieStatus"><fmt:message key="domain.movie.column.movieStatus"/></label>
			<div class="controls">				
				<select id="movieStatus" class="movieStatus">
					<option value="0" <c:if test="${crudObj.movieStatus=='0'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.movieStatus.notspecified"/></option>						
					<option value="3" <c:if test="${crudObj.movieStatus=='1'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.movieStatus.dontwannasee"/></option>
					<option value="1" <c:if test="${crudObj.movieStatus=='2'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.movieStatus.wannasee"/></option>
					<option value="2" <c:if test="${crudObj.movieStatus=='3'}">selected="selected"</c:if>><fmt:message key="domain.movie.column.movieStatus.seenalready"/></option>
				</select> 
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="shelfLocation"><fmt:message key="domain.movie.column.shelfLocation"/></label>
			<div class="controls">
				<input type="text" id="shelfLocation" name="shelfLocation" value="${crudObj.shelfLocation}" maxlength="100" placeholder="<fmt:message key="domain.movie.column.shelfLocation"/>" data-reset="${crudObj.shelfLocation}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="lentTo"><fmt:message key="domain.movie.column.lentTo"/></label>
			<div class="controls">
				<input type="text" id="lentTo" name="lentTo" value="${crudObj.lentTo}" maxlength="100" placeholder="<fmt:message key="domain.movie.column.lentTo"/>" data-reset="${crudObj.lentTo}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="ownMovieNotes"><fmt:message key="domain.movie.column.ownMovieNotes"/></label>
			<div class="controls">
				<textarea id="ownMovieNotes" name="ownMovieNotes" maxlength="500" placeholder="<fmt:message key="domain.movie.column.ownMovieNotes"/>">${crudObj.ownMovieNotes}</textarea>
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
						
		<div class="form-actions">
			<button id="saveButton" type="submit" class="btn btn-primary"><fmt:message key="button.operation.save"/> <fmt:message key="domain.movie"/></button>
			<a href="${pageContext.request.contextPath}/domain/movies/update/${crudObj.id}" id="resetButton" class="btn"><fmt:message key="button.operation.reset"/></a>
			<a href="${pageContext.request.contextPath}/domain/movies/list" id="cancelButton" class="btn"><fmt:message key="button.operation.cancel"/></a>
		</div>	
	</form>
</div>