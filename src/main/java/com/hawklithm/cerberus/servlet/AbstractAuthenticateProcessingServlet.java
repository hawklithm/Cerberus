package com.hawklithm.cerberus.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationCredentialsNotFoundException;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

/**
 * 对servlet即将调用的bean进行一个用户验证和权限验证
 * 
 * @author hawklithm 2013-12-17下午2:27:33
 */
public abstract class AbstractAuthenticateProcessingServlet extends HttpServlet implements
		InitializingBean {

	private static final long serialVersionUID = -2771707028378000131L;
	private MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
	private AuthenticationManager authenticationManager;
	private boolean alwaysReauthenticate = false;

	// private String authenticationFailureUrl;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	final protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, AuthenticationException {
		// TODO Auto-generated method stub
		// try {
		SecurityContextHolder.getContext().setAuthentication(authenticateRequest());
		// } catch (AuthenticationException e) {
		// response.sendRedirect(response.encodeRedirectURL(authenticationFailureUrl));
		// System.out.println("has no right");
		// throw e;
		// }
		try {
			authenticateDoGet(request, response);
		} catch (AccessDeniedException e) {
			accessDenied(request, response);
		}
	}

	protected abstract void accessDenied(HttpServletRequest request, HttpServletResponse response);

	protected abstract void authenticateDoGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	final protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			SecurityContextHolder.getContext().setAuthentication(authenticateRequest());
		} catch (AuthenticationException e) {
			// response.sendRedirect(response.encodeRedirectURL(authenticationFailureUrl));
			System.out.println("has no right");
			// throw e;
		}
		try {
			authenticateDoPost(request, response);
		} catch (AccessDeniedException e) {
			accessDenied(request, response);
		}

	}

	protected abstract void authenticateDoPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	public Authentication authenticateRequest() throws AuthenticationException {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			throw new AuthenticationCredentialsNotFoundException(messages.getMessage(
					"AbstractSecurityInterceptor.authenticationNotFound",
					"An Authentication object was not found in the SecurityContext"));
		}
		Authentication authenticated;

		if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				|| alwaysReauthenticate) {
			try {
				authenticated = this.authenticationManager.authenticate(SecurityContextHolder
						.getContext().getAuthentication());
			} catch (AuthenticationException authenticationException) {
				throw authenticationException;
			}
		} else {
			authenticated = SecurityContextHolder.getContext().getAuthentication();
		}
		return authenticated;
	}

	public boolean isAlwaysReauthenticate() {
		return alwaysReauthenticate;
	}

	public void setAlwaysReauthenticate(boolean alwaysReauthenticate) {
		this.alwaysReauthenticate = alwaysReauthenticate;
	}

	// public String getAuthenticationFailureUrl() {
	// return authenticationFailureUrl;
	// }
	//
	// public void setAuthenticationFailureUrl(String authenticationFailureUrl)
	// {
	// this.authenticationFailureUrl = authenticationFailureUrl;
	// }

	@Override
	public void afterPropertiesSet() throws Exception {
		// Assert.hasLength(authenticationFailureUrl,
		// "authenticationFailureUrl must  be specified");
		Assert.notNull(authenticationManager, "authenticationManager must be specified");
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
