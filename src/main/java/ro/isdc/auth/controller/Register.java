package ro.isdc.auth.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.domain.Role;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.repository.RoleRepository;
import ro.isdc.auth.service.crud.AccountService;

/**
 * Controller used for the sign up/registration process
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
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
	Map<String, ? extends Object> createAccount(AtmosphereResource atmosphereResource, @RequestBody Account accountToCreate, HttpServletResponse response) {
		logger.debug("Entering create account from register method...");
		Role userRole = getRole("ROLE_USER");
		accountToCreate.getRoles().clear();
		accountToCreate.getRoles().add(userRole);
		accountToCreate.setIsEnabled(true);
		Set<ConstraintViolation<Account>> failures = validator.validate(accountToCreate);
		final Broadcaster bc = atmosphereResource.getBroadcaster();
		if (!failures.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			bc.broadcast(HttpServletResponse.SC_BAD_REQUEST);
			return getFailureMessages(failures);
		} else {
			if (service.create(accountToCreate) != null) {
				Collection<GrantedAuthority> grantedAuthorities = toGrantedAuthorities(accountHelper.getRoleNames(accountToCreate));
				Authentication authReq = new UsernamePasswordAuthenticationToken(accountToCreate.getEmail(), accountToCreate.getPassword(), grantedAuthorities);
				SecurityContextHolder.getContext().setAuthentication(authReq);
				bc.broadcast(HttpServletResponse.SC_CREATED);
				response.setStatus(HttpServletResponse.SC_CREATED);
			}
			return null;
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
