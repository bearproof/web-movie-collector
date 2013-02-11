package ro.isdc.auth.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ro.isdc.model.MovieInfo;

/**
 * <p>
 * Movie entity class
 * </p>
 * 
 * @author Adrian.Ursu
 */
@Document
public class Movie extends MovieInfo {

	@Id
	private String id;

	/* @NotNull */
	private String userId;

	/* @NotNull */
	private String idOnSite;

	private String userRating;

	private String movieStatus;

	private String shelfLocation;

	private String lentTo;

	private String ownMovieNotes;

	private Date loanDate;

	private Date returnDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShelfLocation() {
		return shelfLocation;
	}

	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
	}

	public String getLentTo() {
		return lentTo;
	}

	public void setLentTo(String lentTo) {
		this.lentTo = lentTo;
	}

	public String getOwnMovieNotes() {
		return ownMovieNotes;
	}

	public void setOwnMovieNotes(String ownMovieNotes) {
		this.ownMovieNotes = ownMovieNotes;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getUserRating() {
		return userRating;
	}

	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}

	public String getMovieStatus() {
		return movieStatus;
	}

	public void setMovieStatus(String movieStatus) {
		this.movieStatus = movieStatus;
	}

	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

	public String getIdOnSite() {
		return idOnSite;
	}

	public void setIdOnSite(String idOnSite) {
		this.idOnSite = idOnSite;
	}

}
