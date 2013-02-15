package ro.isdc.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.isdc.InfoSourceConfig;
import ro.isdc.model.HtmlNodePathMapper;
import ro.isdc.model.InfoSourceModel;
import ro.isdc.model.SearchInputModel;
import ro.isdc.services.MovieRetriever;
import ro.isdc.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WMCController extends LocaleAwareController {

	@Autowired
	private InfoSourceConfig infoSourceConfig;

	@Autowired
	HtmlNodePathMapper htmlNodePathMapper;

	@Autowired
	@Qualifier("movieRetriever")
	MovieRetriever movieRetriever;
	private static final Logger logger = LoggerFactory.getLogger(WMCController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/movieLand", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		Set<String> infoSources = infoSourceConfig.getSiteConfig().getConfigMap().keySet();
		model.addAttribute("infoSources", infoSources);
		model.addAttribute("currentTemplate", "Basic Template");
		return "searchPage";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public void openChannel(AtmosphereResource atmosphereResource, @RequestBody final String clientData) {
		this.suspend(atmosphereResource);
		final Broadcaster bc = atmosphereResource.getBroadcaster();
		logger.debug("Atmo Resource Size: " + bc.getAtmosphereResources().size());

		if (clientData == null || clientData.isEmpty()) {
			bc.broadcast("");
		} else {
			int max = 10;
			for (int i = 0; i < max; i++) {
				bc.broadcast(clientData + "- Back from server " + i + "/" + max);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public void search(AtmosphereResource atmosphereResource, @RequestBody String searchModelAsJson) throws JsonGenerationException, JsonMappingException, IOException,
			InterruptedException {
		// AtmosphereUtil.suspend(atmosphereResource);

		if (!searchModelAsJson.isEmpty()) {
			SearchInputModel reqSearch = Utils.getJsonAsObject(searchModelAsJson, SearchInputModel.class);
			List<InfoSourceModel> infoSourcesList = infoSourceConfig.getInfoSourcesBriefSearch(reqSearch);
			for (String searchTerm : reqSearch.getSearchTerms()) {
				for (InfoSourceModel infoSourceModel : infoSourcesList) {
					movieRetriever.retrieveMovieData(atmosphereResource, searchTerm, infoSourceModel, htmlNodePathMapper, false); // the
																																	// boolean
																																	// param
																																	// is
																																	// true
																																	// for
																																	// detailed
																																	// data
																																	// request
				}
			}
		}
	}

	@RequestMapping(value = "/searchDetailedData", method = RequestMethod.POST)
	@ResponseBody
	public void searchDetailedData(AtmosphereResource atmosphereResource, @RequestBody String searchModelAsJson) throws JsonGenerationException, JsonMappingException, IOException,
			InterruptedException {
		if (!searchModelAsJson.isEmpty() && searchModelAsJson != null) {
			SearchInputModel requestDetailedData = Utils.getJsonAsObject(searchModelAsJson, SearchInputModel.class);
			InfoSourceModel infoSourceModel = infoSourceConfig.getInfoSourceDetailedSearch(requestDetailedData);
			movieRetriever.retrieveMovieData(atmosphereResource, requestDetailedData.getSearchTerms().get(0), infoSourceModel, htmlNodePathMapper, true);
		}
	}

	private void suspend(final AtmosphereResource resource) {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
			@Override
			public void onSuspend(AtmosphereResourceEvent event) {
				countDownLatch.countDown();
				resource.removeEventListener(this);
			}
		});
		resource.suspend();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
