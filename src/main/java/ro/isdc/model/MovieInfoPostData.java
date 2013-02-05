package ro.isdc.model;

import java.util.Map;

//TODO: Rename 
public class MovieInfoPostData {
	
	/**
	 * String specifying if the web site uses cookies.
	 */
	private String usesCookies;
	
	/**
	 * Value for the URL where the cookies will be retrieved from.
	 */
	private String fetchCookieURL;
	
	/**
	 * Map containing post values for the request
	 */
	private Map<String,String> briefPostBody;
	/**
	 * Map containing  the headers needed for the post request
	 */
	private Map<String, String> briefPostHeaders;
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
	 * @return the fetchCookieURL
	 */
	public String getFetchCookieURL() {
		return fetchCookieURL;
	}
	/**
	 * @param fetchCookieURL the fetchCookieURL to set
	 */
	public void setFetchCookieURL(String fetchCookieURL) {
		this.fetchCookieURL = fetchCookieURL;
	}
	/**
	 * @return the briefPostBody
	 */
	public Map<String, String> getBriefPostBody() {
		return briefPostBody;
	}
	/**
	 * @param briefPostBody the briefPostBody to set
	 */
	public void setBriefPostBody(Map<String, String> briefPostBody) {
		this.briefPostBody = briefPostBody;
	}
	/**
	 * @return the briefPostHeaders
	 */
	public Map<String, String> getBriefPostHeaders() {
		return briefPostHeaders;
	}
	/**
	 * @param briefPostHeaders the briefPostHeaders to set
	 */
	public void setBriefPostHeaders(Map<String, String> briefPostHeaders) {
		this.briefPostHeaders = briefPostHeaders;
	}
	
	
	
	
	
	
	

}
