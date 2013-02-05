package ro.isdc.model;

import java.util.Map;

/**
 * Object containing the configuration data for each website.
 * 
 * @author Adrian.Ursu
 *
 */

public class InfoSourceModel {
	
	/**
	 * String representing if the website need cookie sync ("true" or "false").
	 */
	private String usesCookies;
	
	/**
	 * Map specifying the search URLs to look on. If ussesCookies is true.
	 */
	private Map<String, String> searchURLs;
	
	/**
	 * The http methods to be used for the searchURLs defined. 
	 * have to match the ones from searchURL, but with the suffix Method
	 * instead of URL.
	 */
	private Map<String, String> searchMethods;
	
	/**
	 * Default header-header values to be used for the current website.
	 * This is the place where you can set the User-Agent for example
	 */
	private Map<String, String> presetHeaders;
	
	/**
	 * TODO: TO add the docs for tricky here. 
	 */
	private Map<String, PostData> post;

	/**
	 * @return the usesCookies
	 */
	public String getUsesCookies() {
		return usesCookies;
	}

	/**
	 * @param usesCookies the usesCookies to set
	 */
	public void setUsesCookies(String usesCookies) {
		this.usesCookies = usesCookies;
	}

	/**
	 * @return the searchURLs
	 */
	public Map<String, String> getSearchURLs() {
		return searchURLs;
	}

	/**
	 * @param searchURLs the searchURLs to set
	 */
	public void setSearchURLs(Map<String, String> searchURLs) {
		this.searchURLs = searchURLs;
	}

	/**
	 * @return the searchMethods
	 */
	public Map<String, String> getSearchMethods() {
		return searchMethods;
	}

	/**
	 * @param searchMethods the searchMethods to set
	 */
	public void setSearchMethods(Map<String, String> searchMethods) {
		this.searchMethods = searchMethods;
	}

	/**
	 * @return the presetHeaders
	 */
	public Map<String, String> getPresetHeaders() {
		return presetHeaders;
	}

	/**
	 * @param presetHeaders the presetHeaders to set
	 */
	public void setPresetHeaders(Map<String, String> presetHeaders) {
		this.presetHeaders = presetHeaders;
	}

	/**
	 * @return the post
	 */
	public Map<String, PostData> getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(Map<String, PostData> post) {
		this.post = post;
	}
	
	

}
