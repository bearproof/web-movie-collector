package ro.isdc.auth.service.crud;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.Lists;

import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.support.ReadOperationParams;
import ro.isdc.auth.support.ReadOperationResults;

/**
 * Abstract Crud service
 * 
 * @author maachou
 * 
 */
public abstract class AbstractCrudService<T> {

	public AbstractCrudService() {
		super();
	}

	/**
	 * Getting the repository
	 * 
	 * @return
	 */
	public abstract PagingAndSortingRepository<T, String> getRepository();

	/**
	 * Getting the entity Helper
	 * 
	 * @return
	 */
	public abstract EntityHelper<T> getHelper();

	/**
	 * Create operation
	 * 
	 * @param entity
	 * @return created entity
	 */
	public T create(T entity) {
		return getRepository().save(entity);
	}

	/**
	 * Read Operation
	 * 
	 * @param params
	 * @return dataTable response pojo
	 * @throws UnsupportedEncodingException 
	 */
	public ReadOperationResults read(ReadOperationParams params) throws UnsupportedEncodingException {
		params.setsSearch(URLDecoder.decode(URLEncoder.encode(params.getsSearch(), "ISO-8859-1"),"UTF-8"));
		ReadOperationResults result = new ReadOperationResults();
		Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
		String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
		Sort sort = new Sort(sortDir, sortColName);
		int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
		Page<T> page = getRepository().findAll(new PageRequest(pageNumber, params.getiDisplayLength(), sort));
		List<T> data = page.getContent();
		List<T> uiDate = new ArrayList<T>();
		for (T entity : data) {
			uiDate.add(getHelper().copyFrom(entity));
		}
		result.setsEcho(params.getsEcho());
		result.setiTotalDisplayRecords(page.getTotalElements());
		result.setiTotalRecords(page.getTotalElements());
		result.setAaData(uiDate);
		return result;
	}

	/**
	 * Read all entities
	 * 
	 * @return a list of requested entities
	 */
	public List<T> readAll() {
		return Lists.newArrayList(getRepository().findAll());
	}

	/**
	 * Update operation
	 * 
	 * @param entity
	 * @return
	 */
	public T update(T entity) {
		return getRepository().save(entity);
	}

	/**
	 * Delete operation
	 * 
	 * @param entity
	 */
	public void delete(String id) {
		getRepository().delete(id);
	}

	/**
	 * Get entity by id
	 * 
	 * @param id
	 * @return
	 */
	public T getById(final String id) {
		return getRepository().findOne(id);
	}

	/**
	 * Check if an entity with a given id exists
	 * 
	 * @param id
	 * @return
	 */
	public boolean exists(final String id) {
		return getRepository().exists(id);
	}

}
