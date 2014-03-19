package com.hawklithm.cerberus.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletToBeanProxy extends GenericServlet {

	private static final long serialVersionUID = 3647826724244372681L;
	private WebApplicationContext wac;

	private String targetBean;
	private Servlet proxy;

	public void init() throws ServletException {
		this.targetBean = getInitParameter("targetBean");
		getServletBean();
		proxy.init(getServletConfig());
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException,
			IOException {
		proxy.service(req, res);
	}

	private void getServletBean() {
		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.proxy = (Servlet) wac.getBean(targetBean);
	}

}
