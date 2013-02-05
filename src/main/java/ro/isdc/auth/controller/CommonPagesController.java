package ro.isdc.auth.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Common pages controller
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */
@Controller
public class CommonPagesController {

	private static final Logger logger = Logger.getLogger(CommonPagesController.class);

	/**
	 * Login page
	 * 
	 * @return login page id
	 */
	@RequestMapping(value = { "/login" })
	public String getLoginPage() {
		logger.debug("Calling Login page.");
		return "loginPage";
	}
	/**
	 * Register page
	 * 
	 * @return register page id
	 */
	@RequestMapping(value = { "/register" })
	public String getSignUpPage() {
		logger.debug("Calling Register page.");
		return "registerPage";
	}

	/**
	 * Home Page
	 * 
	 * @return home page id
	 */
	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public String getHomePage() {
		logger.debug("Calling home page.");
		return "homePage";
	}
}
