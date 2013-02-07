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
		return copy;
	}

	@Override
	public Movie updateFrom(Movie fromentity, Movie toEntity) {
		// TODO Not sure this is needed here anymore. Test before removing!!!
		return null;
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
