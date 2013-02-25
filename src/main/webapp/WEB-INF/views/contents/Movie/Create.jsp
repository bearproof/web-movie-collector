<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="formsContent">
	<!-- Form container -->
	<form id="movieForm" class="form-horizontal" autocomplete="off" method="POST" action="">
	<legend><fmt:message key="domain.movie.create.legend"/></legend>
		<div class="control-group">
			<label class="control-label" for="title"><fmt:message key="domain.movie.column.title"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="title" name="title" value="" maxlength="100" placeholder="Movie Title">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="year"><fmt:message key="domain.movie.column.year"/><em>*</em></label>
			<div class="controls">
				<input type="text" id="year" name="year" value="" maxlength="100" placeholder="Year">
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="genre"><fmt:message key="domain.movie.column.genre"/></label>
			<div class="controls">
				<input type="text" id="genre" name="genre" value="" maxlength="100" placeholder="The genre of the movie">
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="director"><fmt:message key="domain.movie.column.director"/></label>
			<div class="controls">
				<input type="text" id="director" name="director" value="" maxlength="100" placeholder="The director">
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="cast"><fmt:message key="domain.movie.column.cast"/></label>
			<div class="controls">
				<textarea id="cast" name="cast" maxlength="200" placeholder="<fmt:message key="domain.movie.column.cast"/>"></textarea>
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="description"><fmt:message key="domain.movie.column.description"/></label>
			<div class="controls">
				<textarea id="description" name="description" maxlength="500" placeholder="Description"></textarea>
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="runtime"><fmt:message key="domain.movie.column.runtime"/></label>
			<div class="controls">
				<input type="text" id="runtime" name="runtime" value="" maxlength="100" placeholder="Runtime (min)">
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="userRating"><fmt:message key="domain.movie.column.userRating"/></label>
			<div class="controls">				
				<select id="userRating" class="userRating">
					<option value="0" selected="selected"><fmt:message key="domain.movie.column.userRating.unrated"/></option>
					<option value="1"><fmt:message key="domain.movie.column.userRating.dontbother"/></option>
					<option value="2"><fmt:message key="domain.movie.column.userRating.prettygood"/></option>
					<option value="3"><fmt:message key="domain.movie.column.userRating.verygood"/></option>
					<option value="4"><fmt:message key="domain.movie.column.userRating.excellent"/></option>
				</select> 
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="movieStatus"><fmt:message key="domain.movie.column.movieStatus"/></label>
			<div class="controls">				
				<select id="movieStatus" class="movieStatus">
					<option value="0" selected="selected"><fmt:message key="domain.movie.column.movieStatus.notspecified"/></option>						
					<option value="1"><fmt:message key="domain.movie.column.movieStatus.wannasee"/></option>
					<option value="2"><fmt:message key="domain.movie.column.movieStatus.seenalready"/></option>
					<option value="3"><fmt:message key="domain.movie.column.movieStatus.dontwannasee"/></option>
				</select> 
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="shelfLocation"><fmt:message key="domain.movie.column.shelfLocation"/></label>
			<div class="controls">
				<input type="text" id="shelfLocation" name="shelfLocation" value="" maxlength="100" placeholder="<fmt:message key="domain.movie.column.shelfLocation"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="lentTo"><fmt:message key="domain.movie.column.lentTo"/></label>
			<div class="controls">
				<input type="text" id="lentTo" name="lentTo" value="" maxlength="100" placeholder="<fmt:message key="domain.movie.column.lentTo"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="ownMovieNotes"><fmt:message key="domain.movie.column.ownMovieNotes"/></label>
			<div class="controls">
				<textarea id="ownMovieNotes" name="ownMovieNotes" maxlength="500" placeholder="<fmt:message key="domain.movie.column.ownMovieNotes"/>"></textarea>
				<!-- <span class="help-inline"></span> -->
			</div>
		</div>
				
		<div class="form-actions">
			<button id="saveButton" type="submit" class="btn btn-primary"><fmt:message key="searchPage.movie.addToDB"/></button>
			<button id="cancelButton" type="button" class="btn"><fmt:message key="button.operation.cancel"/></button>
		</div>		
	</form>
</div>