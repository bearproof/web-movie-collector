package ro.isdc.auth.support;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ro.isdc.auth.service.crud.AbstractCrudService;
import ro.isdc.utils.BasicAjaxResponse;

/**
 * Abstract Rest Controller
 * 
 * @author maachou
 * 
 * @param <T>
 */
public abstract class AbstractRestController<T> {

	private static final Logger logger = Logger.getLogger(AbstractRestController.class);

	protected abstract Validator getValidator();

	protected abstract AbstractCrudService<T> getService();

	/**
	 * CREATE operation handler
	 * 
	 * @param entity
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/", method = POST, consumes = "application/json")
	public @ResponseBody
	BasicAjaxResponse create(@RequestBody T entity, HttpServletResponse response) {
		logger.debug("Creating entity: " + entity.toString());
		Set<ConstraintViolation<T>> failures = getValidator().validate(entity);

		if (!failures.isEmpty()) {
			return getFailureMessages(failures);
		} else {
			if (getService().create(entity) != null) {

				return new BasicAjaxResponse(false, "CREATED");
			}
			return new BasicAjaxResponse(true, "CREATE_FAILED");
		}
	}

	/**
	 * READ all operation handler
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<T> listAll() {
		return getService().readAll();
	}

	/**
	 * READ operation handler
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ReadOperationResults list(ReadOperationParams params) {
		return getService().read(params);
	}

	/**
	 * UPDATE Account Operation
	 * 
	 * @param accountForm
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public @ResponseBody
	BasicAjaxResponse update(@PathVariable String id, @RequestBody T entity, HttpServletResponse response) {
		logger.debug("Updating entity: " + entity.toString());
		Set<ConstraintViolation<T>> failures = getValidator().validate(entity);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return getFailureMessages(failures);
		} else {
			T entityToUpdate = getService().getById(id);
			if (entityToUpdate != null) {
				entityToUpdate = getService().getHelper().updateFrom(entity, entityToUpdate);
				getService().update(entityToUpdate);
				response.setStatus(HttpServletResponse.SC_OK);
				return null;
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
	}

	/**
	 * DELETE Operation
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{pk}", method = DELETE)
	@ResponseBody
	public boolean delete(@PathVariable("pk") String id, HttpServletResponse response) {
		getService().delete(id);
		response.setStatus(HttpServletResponse.SC_OK);
		return true;
	}

	/**
	 * Requesting and entity
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	@ResponseBody
	public T get(@PathVariable("pk") Long id, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

	/**
	 * Generate a map of error messages
	 * 
	 * @param failures
	 * @return
	 */
	protected BasicAjaxResponse getFailureMessages(final Set<ConstraintViolation<T>> failures) {
		BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		String errorMessage = "";
		for (ConstraintViolation<T> failure : failures) {
			errorMessage += failure.getMessage();
		}
		ajaxResponse.setMessage(errorMessage);
		ajaxResponse.setError(true);
		return ajaxResponse;
	}

	/**
	 * Exception handler
	 * 
	 * @param e
	 * @param out
	 */
	@ExceptionHandler()
	@ResponseStatus(value = INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String exception(Exception e) {
		return e.getMessage();
	}
}