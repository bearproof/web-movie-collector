
package ro.isdc.services;

import java.util.List;

import org.apache.http.nio.reactor.IOReactorException;
import org.atmosphere.cpr.AtmosphereResource;

import ro.isdc.model.HtmlNodePathMapper;
import ro.isdc.model.InfoSourceModel;
import ro.isdc.model.SearchInputModel;

/**
 * Interface defining the contract for 
 * movie retriever implementations.
 * 
 * @author Ioana.Mocan
 *
 */
public interface IMovieRetrieverBusinessManager {
	
	/**
	 * Method for retrieving brief information about movies.
	 * 
	 * @param atmosphereResource
	 * 				the Atmosphere resource to which to broadcast result
	 * @param reqSearch
	 * 				the object containing the movies and sites
	 * @throws IOReactorException
	 * @throws InterruptedException
	 */
	void getBriefMoviesResult(AtmosphereResource atmosphereResource,SearchInputModel reqSearch,  List<InfoSourceModel> infoSources,  HtmlNodePathMapper  htmlNodePathMapper) throws IOReactorException, InterruptedException;

}	

