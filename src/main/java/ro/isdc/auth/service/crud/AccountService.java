package ro.isdc.auth.service.crud;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.repository.AccountRepository;
import ro.isdc.auth.support.UserContextUtil;

/**
 * Account service
 * 
 * @author maachou
 * 
 */
@Service
public class AccountService extends AbstractCrudService<Account> {
	
	private static final String PASSWORD_UI = "**********";

	private AccountRepository repository;

	private AccountHelper helper;

	private UserContextUtil userContext;

	@Autowired
	public AccountService(AccountRepository accountRepository, AccountHelper helper, UserContextUtil userContext) {
		this.repository = accountRepository;
		this.helper = helper;
		this.userContext = userContext;
	}

	@Override
	public Account create(Account accountToSave) {
		accountToSave.setPassword(DigestUtils.md5Hex(accountToSave.getPassword()));
		return repository.save(accountToSave);
	}

	@Override
	public PagingAndSortingRepository<Account, String> getRepository() {
		return this.repository;
	}

	@Override
	public EntityHelper<Account> getHelper() {
		return this.helper;
	}

	public Account updateUserAccount(Account userAccountToUpdate) {
		Account accountToSave = repository.findOne(userContext.getUserId());
		accountToSave.setFirstName(userAccountToUpdate.getFirstName());
		accountToSave.setLastName(userAccountToUpdate.getLastName());
		if (!userAccountToUpdate.getPassword().equals(PASSWORD_UI)) {
			accountToSave.setPassword(DigestUtils.md5Hex(userAccountToUpdate.getPassword()));
		}
		
		return repository.save(accountToSave);
	}
}
