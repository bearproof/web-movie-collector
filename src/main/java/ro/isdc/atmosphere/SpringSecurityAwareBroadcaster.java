package ro.isdc.atmosphere;

import javax.servlet.http.HttpSession;

import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class SpringSecurityAwareBroadcaster extends DefaultBroadcaster {

	public SpringSecurityAwareBroadcaster(String name, AtmosphereConfig config) {
		super(name, config);
	}

	@Override
	protected void broadcast(AtmosphereResource r, AtmosphereResourceEvent e) {
		SecurityContext oldContext = SecurityContextHolder.getContext();
		try {
			HttpSession session = r.getRequest().getSession(false);
			if (session == null) {
				throw new InsufficientAuthenticationException("No session");
			}
			SecurityContext context = (SecurityContext) session
					.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
			if (context == null) {
				throw new InsufficientAuthenticationException("No context");
			}
			if (!context.getAuthentication().isAuthenticated()) {
				throw new InsufficientAuthenticationException("Not authenticated");
			}
			SecurityContextHolder.setContext(context);
			super.broadcast(r, e);
		} finally {
			SecurityContextHolder.setContext(oldContext);
		}
	}
}