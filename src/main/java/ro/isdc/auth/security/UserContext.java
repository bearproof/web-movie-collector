package ro.isdc.auth.security;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Get Spring security context to access user data security infos.
 * 
 * @author maachou
 * @author Adrian.Ursu
 */
public class UserContext {
	/**
	 * Get the current username. Note that it may not correspond to a username
	 * that currently exists in your accounts' repository; it could be a spring
	 * security 'anonymous user'.
	 * 
	 * @return the current user's username, or null if none.
	 */
	public static String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}

			return principal.toString();
		}

		return null;
	}

	/**
	 * return the current locale
	 */
	public static Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	/**
	 * Retrieve the current UserDetails bound to the current thread by Spring
	 * Security, if any.
	 */
	public static UserDetails getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if ((auth != null) && (auth.getPrincipal() instanceof UserDetails)) {
			return ((UserDetails) auth.getPrincipal());
		}

		return null;
	}

	/**
	 * Return current roles bound to the current thread by Spring Security
	 * 
	 * @return roles list
	 */
	public static List<String> getRoles() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			List<String> result = newArrayList();

			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				result.add(grantedAuthority.getAuthority());
			}
			return result;
		}
		return Collections.emptyList();
	}

}
