package ro.isdc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Object containing the configuration data 
 * for all available sites.
 * 
 * @author Ioana.Mocan
 *
 */
public class InfoSource {

	/**
	 * Map containing the information for all sites.
	 */
	private Map<String, InfoSourceModel> configMap = new HashMap<String, InfoSourceModel>();

	/**
	 * @return the configMap
	 */
	public Map<String, InfoSourceModel> getConfigMap() {
		return configMap;
	}

	/**
	 * @param configMap the configMap to set
	 */
	public void setConfigMap(Map<String, InfoSourceModel> configMap) {
		this.configMap = configMap;
	}
	
	
	
	
}
