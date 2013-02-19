package ro.isdc.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ro.isdc.auth.domain.Account;

/**
 * Account repository
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {

	Account findByEmail(String email);
	@Query("{$or : [ { firstName : { $regex : ?0, $options : 'i'}} , { lastName : { $regex : ?0, $options : 'i'}}, { email : { $regex : ?0, $options : 'i'}}]}")
	Page<Account> findAllBySearchTerm(String searchTerm, Pageable request);

}
