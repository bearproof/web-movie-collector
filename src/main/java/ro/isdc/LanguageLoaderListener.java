package ro.isdc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ro.isdc.auth.controller.LocaleAwareController;

public class LanguageLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx= sce.getServletContext();
		String lngFolder=ctx.getInitParameter("languageFolder");
		System.out.println("The language folder is: " + lngFolder );
		LocaleAwareController.loadSupportedLanguages(ctx,lngFolder);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
