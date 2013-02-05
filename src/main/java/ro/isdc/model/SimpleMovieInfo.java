package ro.isdc.model;

/**
@author Dan Cirja
*/

public class SimpleMovieInfo {

	private String id;
	private String title;
	private String year;
	private String director;
	private String site;
	

	public SimpleMovieInfo() {
		
	}
	public SimpleMovieInfo(String id, String title, String year, String director, String site) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.site = site;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nID: " + id 
				+ "\nTITLE: " + title 
				+ "\nYEAR: " + year 
				+ "\nDIRECTOR: " + director 
				+ "\nSITE: " + site;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
}
