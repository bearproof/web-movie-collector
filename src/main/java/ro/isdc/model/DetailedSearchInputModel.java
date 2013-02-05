package ro.isdc.model;
/**
 * @author Paul.Tamas
 *
 */

import java.util.ArrayList;
import java.util.List;

public class DetailedSearchInputModel {
	private List<String> movieId = new ArrayList<String>();
	private List<String> infoSource = new ArrayList<String>();
	
	/**
	 * 
	 * @return the movieId of the movie to which we need to get the detailed info
	 */
	public List<String> getMovieId() {
		return movieId;
	}
	
	/**
	 * 
	 * @param movieId set the movieId of the movie to which we need to get the detailed info
	 */
	public void setMovieId(List<String> movieId) {
		this.movieId = movieId;
	}
	
	/**
	 * 
	 * @return the infoSource where we need to look for detailed info
	 */
	public List<String> getInfoSource() {
		return infoSource;
	}
	
	/**
	 * 
	 * @param infoSource set the infoSource where we need to look for detailed info
	 */
	public void setInfoSource(List<String> infoSource) {
		this.infoSource = infoSource;
	}

}
