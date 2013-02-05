package ro.isdc.auth.controller;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.service.crud.AbstractCrudService;
import ro.isdc.auth.service.crud.AccountService;
import ro.isdc.auth.support.AbstractCrudController;

/**
 * Account CRUD controller
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */
@Controller
@RequestMapping("/domain/accounts/")
public class AccountController extends AbstractCrudController<Account> { 

	@Autowired
	private AccountService service;

	@Autowired
	private AccountHelper helper;

	@Autowired
	private Validator validator;

	@Override
	protected String getListPageName() {
		return "accountsPage";
	}

	@Override
	protected String getCreatePageName() {
		return "createAccountPage";
	}

	@Override
	protected String getUpdatePageName() {
		return "updateAccountPage";
	}

	@Override
	protected AbstractCrudService<Account> getService() {
		return this.service;
	}

	@Override
	protected EntityHelper<Account> getHelper() {
		return this.helper;
	}

	@Override
	protected Validator getValidator() {
		return this.validator;
	}
}
