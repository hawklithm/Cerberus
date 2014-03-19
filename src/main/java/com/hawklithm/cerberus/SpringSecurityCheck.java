package com.hawklithm.cerberus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AuthenticationServiceException;

/**
 * Servlet implementation class SpringSecurityCheck
 */
public class SpringSecurityCheck extends HttpServlet {
	private static final long serialVersionUID = -1046131828813286294L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public SpringSecurityCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pwd=request.getParameter("j_password");
		String user=request.getParameter("j_username");
		System.out.println(pwd+'\n'+user);
		
		throw new AuthenticationServiceException("dfasdfsadf");
	}

}
