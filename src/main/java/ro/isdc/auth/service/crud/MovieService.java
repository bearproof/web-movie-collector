package ro.isdc.auth.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

import ro.isdc.auth.domain.Movie;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.helper.MovieHelper;
import ro.isdc.auth.repository.MovieRepository;

public class MovieService extends AbstractCrudService<Movie> {

	private MovieRepository repository;
	private MovieHelper helper;

	@Autowired
	public MovieService(MovieRepository repository, MovieHelper helper) {
		this.repository = repository;
		this.helper = helper;
	}

	@Override
	public PagingAndSortingRepository<Movie, String> getRepository() {
		return this.repository;
	}

	@Override
	public EntityHelper<Movie> getHelper() {
		return this.helper;
	}

	// TODO: Override more methods as I see fit.

}
