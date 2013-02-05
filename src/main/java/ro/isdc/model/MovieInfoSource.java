package ro.isdc.model;

import java.util.Map;

/**
 * Object containing the common data for each site.
 * 
 * @author Ioana.Mocan
 *
 */
//TODO: RENAME TO MovieInfoSource
public class MovieInfoSource {
	
	/**
	 * The url for the site.
	 */
	private String briefSearchURL;
	
	/**
	 * The url for full movie details.
	 */
	private String fullSearchURL;
	
	/**
	 * The HTTP method to be used.
	 */
	private String briefSearchMethod;
	
	/**
	 * The HTTP method to be used for full movie details.
	 */
	private String fullSearchMethod;
	
	/**
	 * Film sheet identifier.
	 */
	private String filmSheetPageIdentifier;
	
	/**
	 * Film list identifier.
	 */
	private String filmListPageIdentifier;
	
	
	private MovieInfoPostData post;
	
	/**
	 * Map containing the data for the POST method for full movie details.
	 */
	//TODO: change this to be like post data for brief. See above Map<String, ConfigInfoSrcPostTO> post 
	private Map<String, String> fullPostData;

	
	/**
	 * @return the filmSheetPageIdentifier
	 */
	public String getFilmSheetPageIdentifier() {
		return filmSheetPageIdentifier;
	}

	/**
	 * @param filmSheetPageIdentifier the filmSheetPageIdentifier to set
	 */
	public void setFilmSheetPageIdentifier(String filmSheetPageIdentifier) {
		this.filmSheetPageIdentifier = filmSheetPageIdentifier;
	}

	/**
	 * @return the filmListPageIdentifier
	 */
	public String getFilmListPageIdentifier() {
		return filmListPageIdentifier;
	}

	/**
	 * @param filmListPageIdentifier the filmListPageIdentifier to set
	 */
	public void setFilmListPageIdentifier(String filmListPageIdentifier) {
		this.filmListPageIdentifier = filmListPageIdentifier;
	}

	
	/**
	 * @return the fullSearchURL
	 */
	public String getFullSearchURL() {
		return fullSearchURL;
	}

	/**
	 * @param fullSearchURL the fullSearchURL to set
	 */
	public void setFullSearchURL(String fullSearchURL) {
		this.fullSearchURL = fullSearchURL;
	}

	/**
	 * @return the fullSearchMethod
	 */
	public String getFullSearchMethod() {
		return fullSearchMethod;
	}

	/**
	 * @param fullSearchMethod the fullSearchMethod to set
	 */
	public void setFullSearchMethod(String fullSearchMethod) {
		this.fullSearchMethod = fullSearchMethod;
	}

	/**
	 * @return the fullPostData
	 */
	public Map<String, String> getFullPostData() {
		return fullPostData;
	}

	/**
	 * @param fullPostData the fullPostData to set
	 */
	public void setFullPostData(Map<String, String> fullPostData) {
		this.fullPostData = fullPostData;
	}
	
	/**
	 * @return the briefSearchURL
	 */
	public String getBriefSearchURL() {
		return briefSearchURL;
	}

	/**
	 * @param briefSearchURL the briefSearchURL to set
	 */
	public void setBriefSearchURL(String briefSearchURL) {
		this.briefSearchURL = briefSearchURL;
	}

	/**
	 * @return the briefSearchMethod
	 */
	public String getBriefSearchMethod() {
		return briefSearchMethod;
	}

	/**
	 * @param briefSearchMethod the briefSearchMethod to set
	 */
	public void setBriefSearchMethod(String briefSearchMethod) {
		this.briefSearchMethod = briefSearchMethod;
	}

	/**
	 * @return the post
	 */
	public MovieInfoPostData getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(MovieInfoPostData post) {
		this.post = post;
	}

	

}
