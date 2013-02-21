package ro.isdc.auth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.service.crud.AccountService;
import ro.isdc.auth.support.UserContextUtil;
import ro.isdc.utils.BasicAjaxResponse;
import ro.isdc.utils.StatusCodes;

@Controller
public class ChangeOwnUserDetails extends LocaleAwareController {

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
	BasicAjaxResponse updateUserAccount(@RequestBody Account updatedAccount, HttpServletResponse response) {
		Set<ConstraintViolation<Account>> failures = validator.validate(updatedAccount);
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return getFailureMessages(failures);
		} else {
			service.updateUserAccount(updatedAccount);
			response.setStatus(HttpServletResponse.SC_OK);

			return new BasicAjaxResponse(false, StatusCodes.updated);
		}
	}

	/**
	 * Generate a map of error messages
	 * 
	 * @param failures
	 * @return
	 */
	protected BasicAjaxResponse getFailureMessages(final Set<ConstraintViolation<Account>> failures) {
		BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		String errorMessage = "";
		for (ConstraintViolation<Account> failure : failures) {
			errorMessage += failure.getMessage();
		}
		ajaxResponse.setMessage(errorMessage);
		ajaxResponse.setError(true);
		return ajaxResponse;
	}

}
