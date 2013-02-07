package ro.isdc.auth.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import ro.isdc.auth.domain.Movie;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.helper.MovieHelper;
import ro.isdc.auth.repository.MovieRepository;
import ro.isdc.auth.support.UserContextUtil;

@Service
public class MovieService extends AbstractCrudService<Movie> {

	private UserContextUtil userContext;
	private MovieRepository repository;
	private MovieHelper helper;

	@Autowired
	public MovieService(MovieRepository repository, MovieHelper helper, UserContextUtil userContext) {
		this.repository = repository;
		this.helper = helper;
		this.userContext = userContext;
	}

	@Override
	public PagingAndSortingRepository<Movie, String> getRepository() {
		return this.repository;
	}

	@Override
	public EntityHelper<Movie> getHelper() {
		return this.helper;
	}

	@Override
	public Movie create(Movie entity) {
		entity.setUserId(userContext.getUserId());
		return repository.save(entity);
	}
	// TODO: Override more methods as I see fit.

}
