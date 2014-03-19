package com.hawklithm.cerberus;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * Application Lifecycle Listener implementation class RootListener
 * 
 */
public class RootListener extends ContextLoaderListener implements ServletContextListener {

	/**
	 * @see ContextLoaderListener#ContextLoaderListener()
	 */
	public RootListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ContextLoaderListener#ContextLoaderListener(WebApplicationContext)
	 */
	public RootListener(WebApplicationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		 this.initWebApplicationContext(arg0.getServletContext());
		 try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("ok");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
