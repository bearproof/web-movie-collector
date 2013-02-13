package ro.isdc.auth.service;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.repository.AccountRepository;

/**
 * 
 * An implementation of Spring Security's UserDetailsService.
 * 
 * @author Maachou
 * @author Adrian.Ursu
 * 
 */

@Service("AuthentificationService")
public class AuthentificationService implements UserDetailsService {

	private static final Logger logger = Logger.getLogger(AuthentificationService.class);

	private AccountRepository userRepository;

	private AccountHelper accountHelper;

	@Autowired
	public AuthentificationService(AccountRepository userRepository, AccountHelper accountHelper/*
																								 * ,
																								 * InitDataService
																								 * initDataService
																								 * ,
																								 * InitMovieService
																								 * initMovieService
																								 */) {
		this.userRepository = userRepository;
		this.accountHelper = accountHelper;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
		if ((email == null) || email.trim().isEmpty()) {
			throw new UsernameNotFoundException("Email is null or empty");
		}

		logger.debug("Checking  users with email: " + email);
		Account user = userRepository.findByEmail(email);
		if (user == null) {
			String errorMsg = "User with email: " + email + " could not be found";
			logger.debug(errorMsg);
			throw new UsernameNotFoundException(errorMsg);
		}

		Collection<GrantedAuthority> grantedAuthorities = toGrantedAuthorities(accountHelper.getRoleNames(user));
		String password = user.getPassword();
		boolean enabled = user.getIsEnabled();
		boolean userNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean userNonLocked = true;

		return new org.springframework.security.core.userdetails.User(email, password, enabled, userNonExpired, credentialsNonExpired, userNonLocked, grantedAuthorities);
	}

	public static Collection<GrantedAuthority> toGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> result = newArrayList();

		for (String role : roles) {
			result.add(new SimpleGrantedAuthority(role));
		}

		return result;
	}
}
