package ro.isdc.auth.controller;

import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.isdc.InfoSourceConfig;

/**
 * Common pages controller
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */
@Controller
public class CommonPagesController extends LocaleAwareController {

	private static final Logger logger = Logger.getLogger(CommonPagesController.class);

	@Autowired
	private InfoSourceConfig infoSourceConfig;

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
	 * Home Page
	 * 
	 * @return home page id
	 */
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String getHomePage() {
		logger.debug("Calling home page.");
		return "homePage";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/movieLand", "/" }, method = RequestMethod.GET)
	public String getMovieSearchPage(Locale locale, Model model) {
		Set<String> infoSources = infoSourceConfig.getSiteConfig().getConfigMap().keySet();
		model.addAttribute("infoSources", infoSources);
		model.addAttribute("currentTemplate", "Basic Template");
		return "searchPage";
	}
}
