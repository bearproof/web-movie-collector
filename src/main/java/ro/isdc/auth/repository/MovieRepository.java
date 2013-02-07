package ro.isdc.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ro.isdc.auth.domain.Movie;

/**
 * Movie repository
 * 
 * @author Adrian.Ursu
 * 
 */
public interface MovieRepository extends PagingAndSortingRepository<Movie, String> {

	// TODO Create different queries by requested functionality
	
	Movie findByIdOnSiteAndUserId(String idOnSite, String userId);

}
