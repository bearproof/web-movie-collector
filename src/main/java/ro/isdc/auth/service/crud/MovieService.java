package ro.isdc.auth.service.crud;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import ro.isdc.auth.domain.Movie;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.helper.MovieHelper;
import ro.isdc.auth.repository.MovieRepository;
import ro.isdc.auth.support.ReadOperationParams;
import ro.isdc.auth.support.ReadOperationResults;
import ro.isdc.auth.support.UserContextUtil;

@Service
public class MovieService extends AbstractCrudService<Movie> {

	private UserContextUtil userContext;
	private MovieRepository repository;
	private MovieHelper helper;
	private MongoOperations mongoOps;

	@Autowired
	public MovieService(MovieRepository repository, MovieHelper helper, UserContextUtil userContext, MongoOperations mongoOps) {
		this.repository = repository;
		this.helper = helper;
		this.userContext = userContext;
		this.mongoOps = mongoOps;
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
	public Movie create(Movie movieToSave) {

		String uId = userContext.getUserId();
		if (movieToSave.getIdOnSite() == null)
			movieToSave.setIdOnSite("mymovie");

		if (repository.findByIdOnSiteAndUserId(movieToSave.getIdOnSite(), uId) != null && !movieToSave.getIdOnSite().equalsIgnoreCase("mymovie")) {
			return null;
		} else {
			movieToSave.setUserId(uId);
			return repository.save(movieToSave);
		}

	}

	@Override
	public ReadOperationResults read(ReadOperationParams params) throws UnsupportedEncodingException{

		String uId = userContext.getUserId();

		params.setsSearch(URLDecoder.decode(URLEncoder.encode(params.getsSearch(), "ISO-8859-1"),"UTF-8"));
		ReadOperationResults result = new ReadOperationResults();
		Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
		String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
		Sort sort = new Sort(sortDir, sortColName);
		int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

		PageRequest pageReq = new PageRequest(pageNumber, params.getiDisplayLength(), sort);

		List<Movie> data;
		Page<Movie> page;
		// TODO: To change that
		if (!params.getsSearch().isEmpty()) {
			page = repository.findAllBySearchTerm(params.getsSearch(), uId, pageReq);
		} else {
			page = repository.findByUserId(uId, pageReq);
		}
		data = page.getContent();
		result.setiTotalDisplayRecords(page.getTotalElements());
		result.setiTotalRecords(page.getTotalElements());
		result.setsEcho(params.getsEcho());
		List<Movie> uiDate = new ArrayList<Movie>();
		for (Movie entity : data) {
			uiDate.add(getHelper().copyFrom(entity));
		}

		result.setAaData(uiDate);
		return result;
	}

	@Override
	public List<Movie> readAll() {
		String uId = userContext.getUserId();

		return new ArrayList<Movie>(repository.findByUserId(uId));
	}

	// TODO: We have to check so that the user cannot execute CRUD queries (only
	// find ones)
	public List<Movie> executeCustomQuery(String jsonQuery) {
		BasicQuery query = new BasicQuery(jsonQuery);
		return mongoOps.find(query, Movie.class);

	}
	// TODO: Override more methods as I see fit.

}
