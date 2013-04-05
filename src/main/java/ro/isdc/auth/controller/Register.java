package ro.isdc.auth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.domain.Role;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.repository.RoleRepository;
import ro.isdc.auth.service.crud.AccountService;
import ro.isdc.utils.BasicAjaxResponse;
import ro.isdc.utils.EmailSender;
import ro.isdc.utils.StatusCodes;

/**
 * Controller used for the sign up/registration process
 * 
 * @author maachou
 * @author Adrian.Ursu
 * @author Paul.Tamas
 */
@Controller
public class Register extends LocaleAwareController {
	private static final Logger logger = Logger.getLogger(Register.class);

	@Autowired
	private Validator validator;
	@Autowired
	private AccountService service;
	@Autowired
	RoleRepository roleRepository;
	private @Autowired
	AccountHelper accountHelper;
	
	/**
	 * Activate the account with the id sent as parameter
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = { "/register/{id}" }, method = GET)
	public String activateAccount(@PathVariable("id") String id) {
		logger.debug("Entering activateAccount method for the user with id: "+id);
		Account accountToActivate = service.getById(id);
		if (accountToActivate != null && accountToActivate.getIsEnabled()==false) {
			accountToActivate.setIsEnabled(true);
			service.update(accountToActivate);
			return "activationPage";	
		}else{			
			return "loginPage";
		} 
	}

	/**
	 * Register page
	 * 
	 * @return register page id
	 */
	@RequestMapping(value = { "/register" }, method = GET)
	public String getSignUpPage() {
		logger.debug("Calling Register page.");
		return "registerPage";
	}
	

	@RequestMapping(value = { "/register" }, method = POST, consumes = "application/json")
	public @ResponseBody
		/*Map<String, ? extends Object> */
		BasicAjaxResponse createAccount(AtmosphereResource atmosphereResource, @RequestBody Account accountToCreate, HttpServletResponse response) throws IOException {
		logger.debug("Entering create  account from register method...");
		Role userRole = getRole("ROLE_USER");
		accountToCreate.getRoles().clear();
		accountToCreate.getRoles().add(userRole);
		accountToCreate.setIsEnabled(false);
		Set<ConstraintViolation<Account>> failures = validator.validate(accountToCreate);
		final Broadcaster bc = atmosphereResource.getBroadcaster();
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			bc.broadcast(HttpServletResponse.SC_BAD_REQUEST);
			return (BasicAjaxResponse) getFailureMessages(failures);
		} else {
			if (service.create(accountToCreate) != null) {
				Collection<GrantedAuthority> grantedAuthorities = toGrantedAuthorities(accountHelper.getRoleNames(accountToCreate));
				Authentication authReq = new UsernamePasswordAuthenticationToken(accountToCreate.getEmail(), accountToCreate.getPassword(), grantedAuthorities);
				SecurityContextHolder.getContext().setAuthentication(authReq);		
				//String servletPath = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getServletPath();
				String url = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURL()+"/"+accountToCreate.getId();
				//url = url.replace(servletPath,"/activate");
				new EmailSender().sendConfirmationEmail(accountToCreate.getEmail(), url);				
				return new BasicAjaxResponse(false, StatusCodes.created);
			}
			return new BasicAjaxResponse(false, StatusCodes.create_failed);
		}
	}

	/**
	 * Generate a map of error messages
	 * 
	 * @param failures
	 * @return
	 */
	private Map<String, String> getFailureMessages(final Set<ConstraintViolation<Account>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Account> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}

	private Role getRole(final String roleId) {
		Role result = roleRepository.findByRoleName(roleId);
		if (result == null) {
			result = new Role();
			result.setRoleName(roleId);
			roleRepository.save(result);
		}
		return result;
	}

	public static Collection<GrantedAuthority> toGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			result.add(new SimpleGrantedAuthority(role));
		}

		return result;
	}
}
