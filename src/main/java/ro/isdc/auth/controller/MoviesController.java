package ro.isdc.auth.controller;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.isdc.auth.domain.Movie;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.helper.MovieHelper;
import ro.isdc.auth.service.crud.AbstractCrudService;
import ro.isdc.auth.service.crud.MovieService;
import ro.isdc.auth.support.AbstractCrudController;

@Controller
@RequestMapping("/domain/movies/")
public class MoviesController extends AbstractCrudController<Movie> {

	@Autowired
	private MovieService service;

	@Autowired
	private MovieHelper helper;

	@Autowired
	private Validator validator;

	@Override
	protected String getListPageName() {
		return "moviesPage";
	}

	@Override
	protected String getCreatePageName() {
		return "createMoviePage";
	}

	@Override
	protected String getUpdatePageName() {
		return "updateMoviePage";
	}

	@Override
	protected AbstractCrudService<Movie> getService() {
		return this.service;
	}

	@Override
	protected EntityHelper<Movie> getHelper() {
		return this.helper;
	}

	@Override
	protected Validator getValidator() {
		return this.validator;
	}

}
