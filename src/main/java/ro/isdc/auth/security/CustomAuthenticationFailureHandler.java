package ro.isdc.auth.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {	
	  if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
		  response.sendRedirect("/login?badCredentials=true");		  
	  } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
		  response.sendRedirect("/login?accountDisabled=true");
	  }
	}
	
}
