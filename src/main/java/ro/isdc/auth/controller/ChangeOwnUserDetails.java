package ro.isdc.auth.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.service.crud.AccountService;
import ro.isdc.auth.support.UserContextUtil;

@Controller
public class ChangeOwnUserDetails {

	private static final Logger logger = Logger.getLogger(ChangeOwnUserDetails.class);

	private static final String MODEL_BEAN_ID = "crudObj";
	private static final String PASSWORD_UI = "**********";

	@Autowired
	private AccountService service;

	@Autowired
	private UserContextUtil userContext;

	@Autowired
	private Validator validator;

	/**
	 * Update Own Account page
	 * 
	 * @return update account page id
	 */
	@RequestMapping(value = { "/changeDetails/updateUserAccount" }, method = GET)
	public String getUpdateUserAccountPage(Model model) {
		logger.debug("Calling Update Own User Account page.");

		Account userAccount = service.getById(userContext.getUserId());
		userAccount.setPassword(PASSWORD_UI);
		model.addAttribute(MODEL_BEAN_ID, userAccount);
		return "updateAccountPage";
	}

	/**
	 * UPDATE Account Operation
	 * 
	 * @param accountForm
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/changeDetails/updateUserAccount", method = PUT, consumes = "application/json")
	public @ResponseBody
	Map<String, ? extends Object> updateUserAccount(@RequestBody Account updatedAccount, HttpServletResponse response) {
		Set<ConstraintViolation<Account>> failures = validator.validate(updatedAccount);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return getFailureMessages(failures);
		} else {
			service.updateUserAccount(updatedAccount);
			response.setStatus(HttpServletResponse.SC_OK);

			return null;
		}
	}

	/**
	 * Generate a map of error messages
	 * 
	 * @param failures
	 * @return
	 */
	protected Map<String, String> getFailureMessages(final Set<ConstraintViolation<Account>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Account> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}

	/**
	 * Exception handler
	 * 
	 * @param e
	 * @param out
	 */
	/*
	 * @ExceptionHandler()
	 * 
	 * @ResponseStatus(value = INTERNAL_SERVER_ERROR)
	 * 
	 * @ResponseBody public String exception(Exception e) { return
	 * e.getMessage(); }
	 */

}
