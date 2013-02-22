package ro.isdc.utils;

import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import ro.isdc.model.InfoSourceModel;
import ro.isdc.model.MovieInfoPostData;

public class Utils {

	private static final String REX_TEMPLATE_VALUE = "\\$\\{(\\w+)\\}";

	public static String removeAccents(String text) {

		return text == null ? null : Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	/**
	 * 
	 * Returns a string with accent to REGEX expression to find any combinations
	 * 
	 * in accent insensitive way
	 * 
	 * 
	 * 
	 * @param string
	 *            text The text.
	 * 
	 * @return string The REGEX text.
	 */
	public static String accentToRegex(String searchTerm) {

		try {
			searchTerm = searchTerm.replaceAll("[yýÿ]", "[yýÿ]");
			searchTerm = searchTerm.replaceAll("[uùúûüµ]", "[uùúûüµ]");
			searchTerm = searchTerm.replaceAll("[oòóôõöøðœ]", "[oòóôõöøðœ]");
			searchTerm = searchTerm.replaceAll("[nñ]", "[nñ]");
			searchTerm = searchTerm.replaceAll("[iìíîïĩ]", "[iìíîïĩ]");
			searchTerm = searchTerm.replaceAll("[eèéêëẽ]", "[eèéêëẽ]");
			searchTerm = searchTerm.replaceAll("[cç]", "[cç]");
			searchTerm = searchTerm.replaceAll("[aàáâãäåæ]", "[aàáâãäåæ]");
			searchTerm = searchTerm.replaceAll("[sßš]", "[sßš]");
			searchTerm = searchTerm.replaceAll("[zž]", "[zž]");
			searchTerm = searchTerm.replaceAll("[UÙÚÛÜ]", "[UÙÚÛÜ]");
			searchTerm = searchTerm.replaceAll("[OÒÓÔÕÖØŒ]", "[OÒÓÔÕÖØŒ]");
			searchTerm = searchTerm.replaceAll("[NÑ]", "[NÑ]");
			searchTerm = searchTerm.replaceAll("[DÐ]", "[DÐ]");
			searchTerm = searchTerm.replaceAll("[IÌÍÎÏĨ]", "[IÌÍÎÏĨ]");
			searchTerm = searchTerm.replaceAll("[EÈÉÊËẼ]", "[EÈÉÊËẼ]");
			searchTerm = searchTerm.replaceAll("[CÇ]", "[CÇ]");
			searchTerm = searchTerm.replaceAll("[AÀÁÂÃÄÅÆ]", "[AÀÁÂÃÄÅÆ]");
			searchTerm = searchTerm.replaceAll("[YŸ¥Ý]", "[YŸ¥Ý]");
			searchTerm = searchTerm.replaceAll("[SŠ]", "[SŠ]");
			searchTerm = searchTerm.replaceAll("[ZŽ]", "[ZŽ]");

		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		} catch (IllegalArgumentException ex) {
			// Syntax error in the replacement text (unescaped $ signs?)
		} catch (IndexOutOfBoundsException ex) {
			// Non-existent backreference used the replacement text
		}
		return searchTerm;
	}

	public static HttpUriRequest getRequestForBriefMovieData(InfoSourceModel infoSource, String movieName) {

		HttpUriRequest request = null;
		movieName = EncodingUtil.encodeURIComponent(movieName);
		if (infoSource.getSearchMethods().get("briefSearchMethod").equalsIgnoreCase("get")) {
			request = new HttpGet(infoSource.getSearchURLs().get("briefSearchURL").replace("{title}", movieName));

		} else if (infoSource.getSearchMethods().get("briefSearchMethod").equalsIgnoreCase("post")) {
			request = new HttpPost(infoSource.getSearchURLs().get("briefSearchURL"));
		}

		return request;
	}

	public static HttpUriRequest getRequestForDetailedMovieData(InfoSourceModel infoSource, String movieId) {

		HttpUriRequest request = null;

		if (infoSource.getSearchMethods().get("fullSearchMethod").equalsIgnoreCase("get")) {
			request = new HttpGet(infoSource.getSearchURLs().get("fullSearchURL").replace("{movieId}", movieId));

		} else if (infoSource.getSearchMethods().containsKey("post")) {
			request = new HttpPost(infoSource.getSearchURLs().get("fullSearchURL"));
		}

		return request;

	}

	public static boolean isTemplatedValue(final String val) {
		boolean foundMatch = false;
		try {
			Pattern regex = Pattern.compile(REX_TEMPLATE_VALUE);
			Matcher fieldNameMatcher = regex.matcher(val);
			foundMatch = fieldNameMatcher.find();
		} catch (PatternSyntaxException ex) {

		}
		return foundMatch;
	}

	public static Map<String, String> resolveCookieTokens(final String joinedCookies) {
		Map<String, String> result = new HashMap<String, String>();
		String[] tokensArray = joinedCookies.split(";");
		for (int i = 0; i < tokensArray.length; i++) {
			String[] currentTokens = tokensArray[i].split("=");
			String tokenKey = null;
			String tokenValue = null;
			if (currentTokens.length >= 2) {
				tokenKey = currentTokens[0].trim();
				tokenValue = currentTokens[1].trim();
			}
			result.put(tokenKey, tokenValue);
		}
		return result;
	}

	// "cookies":"invariant part1 jnk ${xr} bull 2 ; mere  pere ${PHPSESSID}; prune ${_sessionid}; prune2"
	public static String resolveTemplate(final String tmpl, final Map<String, String> replacements) {
		String result = tmpl;
		try {
			Pattern regex = Pattern.compile(REX_TEMPLATE_VALUE);
			Matcher fieldNameMatcher = regex.matcher(tmpl);
			while (fieldNameMatcher.find()) {
				try {
					// You can vary the replacement text for each match
					// on-the-fly
					String replacementKey = fieldNameMatcher.group(1);
					String replacementToken = replacements.get(replacementKey);
					String replacement = replacementKey + '=' + replacementToken + "; ";
					result = result.replaceAll("\\$\\{" + replacementKey + "\\}", replacement);
				} catch (IllegalStateException ex) {
				} catch (IllegalArgumentException ex) {
				} catch (IndexOutOfBoundsException ex) {
				}
			}
		} catch (PatternSyntaxException ex) {
		}
		return result;
	}

	/**
	 * Generic method for transforming a json to an object.
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param objectType
	 * @return
	 */
	public static <T extends Object> T getJsonAsObject(String jsonString, Class<T> objectType) {
		Object jsonAsObject = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonAsObject = mapper.readValue(jsonString, objectType);
		} catch (JsonParseException e2) {

			e2.printStackTrace();
		} catch (JsonMappingException e2) {

			e2.printStackTrace();
		} catch (IOException e2) {

			e2.printStackTrace();
		}
		return objectType.cast(jsonAsObject);
	}

	public static HttpParams getPostHttpParams(MovieInfoPostData postConfig) {

		HttpParams postParams = new BasicHttpParams();

		return null;

	}

	public static void setHeaders(Map<String, String> briefPostHeaders, HttpUriRequest syncRequest) {
		Iterator it = briefPostHeaders.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Entry<String, String>) it.next();
			if (!isTemplatedValue(pairs.getValue())) {
				syncRequest.addHeader(pairs.getKey(), pairs.getValue());
			}
		}

	}

	public static void setHeaders(Map<String, String> briefPostBody, HttpUriRequest postRequest, String movieName) {

		Iterator it = briefPostBody.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Entry<String, String>) it.next();
			if (pairs.getValue().contains("{title}")) {
				// pairs.setValue(pairs.getValue().replace("{title}",
				// movieName));
				postRequest.addHeader(pairs.getKey(), pairs.getValue().replace("{title}", movieName));
			} else
				postRequest.addHeader(pairs.getKey(), pairs.getValue());
		}
	}
}
