package ro.isdc.auth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.security.UserContext;
import ro.isdc.auth.service.crud.AbstractCrudService;
import ro.isdc.auth.service.crud.AccountService;
import ro.isdc.auth.support.AbstractCrudController;
import ro.isdc.auth.support.UserContextUtil;

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

	private static final String MODEL_BEAN_ID = "crudObj";

	@Autowired
	private UserContextUtil userContext;

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

	/**
	 * UPDATE User Account Operation
	 * 
	 * @param accountForm
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateUserAccount/{id}", method = GET)
	public String updateUserAccount(@PathVariable("id") String id, Model model) {
		Account userAccount = service.getById(userContext.getUserId());

		model.addAttribute(MODEL_BEAN_ID, userAccount);
		return getUpdatePageName();
	}

	/**
	 * UPDATE Account Operation
	 * 
	 * @param accountForm
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateUserAccount/{id}", method = PUT, consumes = "application/json")
	public @ResponseBody
	Map<String, ? extends Object> update(@PathVariable String id, @RequestBody Account updatedAccount, HttpServletResponse response) {
		Set<ConstraintViolation<Account>> failures = getValidator().validate(updatedAccount);
		if (!failures.isEmpty()) {
			// An error message here for validation
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return getFailureMessages(failures);
		} else {
			// Everything is OK, we should save the account
			service.updateUserAccount(updatedAccount);
			response.setStatus(HttpServletResponse.SC_OK);

			return null;
			/*
			 * if (entityToUpdate != null) { entityToUpdate =
			 * getService().getHelper().updateFrom(entity, entityToUpdate);
			 * getService().update(entityToUpdate);
			 * response.setStatus(HttpServletResponse.SC_OK); return null; }
			 * else { response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			 * return null; }
			 */
		}
	}
}
