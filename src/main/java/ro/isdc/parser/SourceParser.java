package ro.isdc.parser;

import java.util.List;

import ro.isdc.model.HtmlNodePathMapper;
import ro.isdc.model.MovieInfo;
import ro.isdc.model.SimpleMovieInfo;

public interface SourceParser {
		
	public List<SimpleMovieInfo> getMoviesByTitle(final String htmlContent, final String websiteId, final HtmlNodePathMapper htmlNodePathMapper, final String searchTerm);
	
	public MovieInfo getMovieDetails(final String htmlContent, final String websiteId, final HtmlNodePathMapper htmlNodePathMapper);

}
