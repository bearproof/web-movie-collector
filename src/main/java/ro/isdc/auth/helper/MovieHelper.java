package ro.isdc.auth.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.isdc.auth.domain.Movie;
import ro.isdc.auth.support.UserContextUtil;

@Component
public class MovieHelper implements EntityHelper<Movie> {

	@Autowired
	private UserContextUtil userContext;

	@Override
	public Movie copyFrom(Movie entity) {
		Movie copy = new Movie();

		copy.setId(entity.getId());
		copy.setLentTo(entity.getLentTo());
		copy.setLoanDate(entity.getLoanDate());
		copy.setMovieStatus(entity.getMovieStatus());
		copy.setOwnMovieNotes(entity.getOwnMovieNotes());
		copy.setReturnDate(entity.getReturnDate());
		copy.setShelfLocation(entity.getShelfLocation());
		copy.setUserId(entity.getUserId());
		copy.setUserRating(entity.getUserRating());
		copy.setRate(entity.getRate());
		copy.setDescription(entity.getDescription());
		copy.setCast(entity.getCast());
		copy.setGenre(entity.getGenre());
		copy.setRuntime(entity.getRuntime());
		copy.setSite(entity.getSite());
		copy.setTitle(entity.getTitle());
		copy.setYear(entity.getYear());
		copy.setDirector(entity.getDirector());
		return copy;
	}

	@Override
	public Movie copyWithoutPkFrom(Movie entity) {

		Movie copy = new Movie();

		copy.setLentTo(entity.getLentTo());
		copy.setLoanDate(entity.getLoanDate());
		copy.setMovieStatus(entity.getMovieStatus());
		copy.setOwnMovieNotes(entity.getOwnMovieNotes());
		copy.setReturnDate(entity.getReturnDate());
		copy.setShelfLocation(entity.getShelfLocation());
		copy.setUserId(entity.getUserId());
		copy.setUserRating(entity.getUserRating());
		copy.setRate(entity.getRate());
		copy.setDescription(entity.getDescription());
		copy.setCast(entity.getCast());
		copy.setGenre(entity.getGenre());
		copy.setRuntime(entity.getRuntime());
		copy.setSite(entity.getSite());
		copy.setTitle(entity.getTitle());
		copy.setYear(entity.getYear());
		copy.setDirector(entity.getDirector());
		return copy;
	}

	@Override
	public Movie updateFrom(Movie fromEntity, Movie toEntity) {
		toEntity.setLentTo(fromEntity.getLentTo());
		toEntity.setLoanDate(fromEntity.getLoanDate());
		toEntity.setMovieStatus(fromEntity.getMovieStatus());
		toEntity.setOwnMovieNotes(fromEntity.getOwnMovieNotes());
		toEntity.setReturnDate(fromEntity.getReturnDate());
		toEntity.setShelfLocation(fromEntity.getShelfLocation());
		toEntity.setUserRating(fromEntity.getUserRating());
		toEntity.setRate(fromEntity.getRate());
		toEntity.setDescription(fromEntity.getDescription());
		toEntity.setCast(fromEntity.getCast());
		toEntity.setGenre(fromEntity.getGenre());
		toEntity.setRuntime(fromEntity.getRuntime());
		toEntity.setSite(fromEntity.getSite());
		toEntity.setTitle(fromEntity.getTitle());
		toEntity.setYear(fromEntity.getYear());
		toEntity.setDirector(fromEntity.getDirector());

		return toEntity;
	}

	@Override
	public Movie createEntityInstance() {

		Movie movie = new Movie();
		movie.setUserId(userContext.getUserId());
		return movie;
	}

	@Override
	public Movie createRandomEntity() {
		// TODO Not sure we need this Test before removing!!!
		return null;
	}

}
