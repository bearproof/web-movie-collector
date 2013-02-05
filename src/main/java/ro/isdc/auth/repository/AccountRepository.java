package ro.isdc.auth.repository;

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

}
