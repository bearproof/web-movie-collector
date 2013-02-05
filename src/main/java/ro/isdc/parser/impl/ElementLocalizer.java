package ro.isdc.parser.impl;

import java.util.List;

import ro.isdc.model.HtmlNodePathMapper;
import ro.isdc.model.MovieInfo;
import ro.isdc.model.SimpleMovieInfo;

public interface ElementLocalizer {
	
	public List<SimpleMovieInfo> getMoviesByTitle(String htmlContent, String websiteId, HtmlNodePathMapper htmlNodePathMapper, String searchTerm);
	
	public MovieInfo getMovieDetails(String htmlContent, String websiteId, HtmlNodePathMapper websitesXPATHMapper);
}
