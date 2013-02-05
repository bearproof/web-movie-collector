package ro.isdc.model;

import java.util.ArrayList;
import java.util.List;

public class SearchInputModel {
	
	private List<String> searchTerms = new ArrayList<String>();
	private List<String> infoSourceKeys = new ArrayList<String>();


	/**
	 * @return the searchTerms
	 */
	public List<String> getSearchTerms() {
		return searchTerms;
	}



	/**
	 * @param searchTerms the searchTerms to set
	 */
	public void setSearchTerms(List<String> searchTerms) {
		this.searchTerms = searchTerms;
	}



	/**
	 * @return the infoSourceKeys
	 */
	public List<String> getInfoSourceKeys() {
		return infoSourceKeys;
	}



	/**
	 * @param infoSourceKeys the infoSourceKeys to set
	 */
	public void setInfoSourceKeys(List<String> infoSourceKeys) {
		this.infoSourceKeys = infoSourceKeys;
	}
	

	@Override
	public String toString() {
		String returnValue = "\n movies: ";
		
		for(int i=0; i < searchTerms.size(); i++)
		{
			returnValue += "\n" + searchTerms.get(i);
		}
		
		returnValue += "\n sites: ";
				for (int i = 0; i< infoSourceKeys.size(); i++)
				{
					returnValue += "\n" + infoSourceKeys.get(i) ;
				}
		return returnValue;
	}
}
