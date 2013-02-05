package ro.isdc.auth.controller;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.isdc.auth.domain.Role;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.helper.RoleHelper;
import ro.isdc.auth.service.crud.AbstractCrudService;
import ro.isdc.auth.service.crud.RoleService;
import ro.isdc.auth.support.AbstractCrudController;

/**
 * Role CRUD Controller
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */

@Controller
@RequestMapping("/domain/roles/")
public class RoleController extends AbstractCrudController<Role> {

	@Autowired
	private RoleService service;

	@Autowired
	private RoleHelper helper;

	@Autowired
	private Validator validator;

	@Override
	protected String getListPageName() {
		return "rolesPage";
	}

	@Override
	protected String getCreatePageName() {
		return "createRolePage";
	}

	@Override
	protected String getUpdatePageName() {
		return "updateRolePage";
	}

	@Override
	protected AbstractCrudService<Role> getService() {
		return this.service;
	}

	@Override
	protected EntityHelper<Role> getHelper() {
		return this.helper;
	}

	@Override
	protected Validator getValidator() {
		return this.validator;
	}
}
