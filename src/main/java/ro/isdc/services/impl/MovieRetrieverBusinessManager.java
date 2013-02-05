package ro.isdc.services.impl;

import java.util.List;

import org.apache.http.nio.reactor.IOReactorException;
import org.atmosphere.cpr.AtmosphereResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.isdc.InfoSourceConfig;
import ro.isdc.model.HtmlNodePathMapper;
import ro.isdc.model.InfoSourceModel;
import ro.isdc.model.SearchInputModel;
import ro.isdc.services.IMovieRetrieverBusinessManager;
import ro.isdc.services.MovieRetriever;

@Component("movieRetrieverBM")
public class MovieRetrieverBusinessManager implements
		IMovieRetrieverBusinessManager {

	@Autowired
	private InfoSourceConfig configApp;
	@Autowired
	private MovieRetriever retriever;
	@Override
	public void getBriefMoviesResult(AtmosphereResource atmosphereResource, SearchInputModel reqSearch,
			List<InfoSourceModel> infoSources,  HtmlNodePathMapper  htmlNodePathMapper) throws IOReactorException,
			InterruptedException {

		//retriever.execute(reqSearch, infoSources, atmosphereResource, htmlNodePathMapper);

	}


	/**
	 * @return the configApp
	 */
	public InfoSourceConfig getConfigApp() {
		return configApp;
	}

	/**
	 * @param configApp
	 *            the configApp to set
	 */
	public void setConfigApp(InfoSourceConfig configApp) {
		this.configApp = configApp;
	}

	
	
	


}

