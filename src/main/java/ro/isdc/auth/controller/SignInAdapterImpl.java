package ro.isdc.auth.controller;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.repository.AccountRepository;

public class SignInAdapterImpl implements SignInAdapter {

	private static final Logger logger = Logger.getLogger(SignInAdapterImpl.class);
	/*
	 * private @Autowired MongoConnectionService service;
	 */
	private @Autowired
	AccountRepository userRepository;

	private @Autowired
	AccountHelper accountHelper;

	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {

		logger.debug("We are in the sign in adapter");

		Account user = userRepository.findOne(userId);

		Collection<GrantedAuthority> grantedAuthorities = toGrantedAuthorities(accountHelper.getRoleNames(user));
		Authentication authReq = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authReq);
		return null;
	}

	public static Collection<GrantedAuthority> toGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> result = newArrayList();

		for (String role : roles) {
			result.add(new SimpleGrantedAuthority(role));
		}

		return result;
	}

}
