package ro.isdc.auth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
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

	Movie findByDescription(String description);

	List<Movie> findByUserId(String userId);

	Page<Movie> findByUserId(String userId, Pageable request);

	@Query("{$and: [{userId : ?1},{ $or : [ { title : { $regex : ?0, $options : 'i'}} , { year : { $regex : ?0, $options : 'i'}} , { genre : { $regex : ?0, $options : 'i'}} , { director : { $regex : ?0, $options : 'i'}} , { runtime : { $regex : ?0, $options : 'i'}} , { cast : { $regex : ?0, $options : 'i'}}]}]}")
	Page<Movie> findAllBySearchTerm(String searchTerm, String userId, Pageable request);
}
