package ro.isdc.auth.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import ro.isdc.auth.domain.Role;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.helper.RoleHelper;
import ro.isdc.auth.repository.RoleRepository;

/**
 * Role Service
 * 
 * @author maachou
 * 
 */
@Service
public class RoleService extends AbstractCrudService<Role> {

	private RoleRepository repository;

	private RoleHelper helper;

	@Autowired
	public RoleService(RoleRepository repository, RoleHelper helper) {
		this.repository = repository;
		this.helper = helper;
	}

	@Override
	public PagingAndSortingRepository<Role, String> getRepository() {
		return this.repository;
	}

	@Override
	public EntityHelper<Role> getHelper() {
		return this.helper;
	}

}
