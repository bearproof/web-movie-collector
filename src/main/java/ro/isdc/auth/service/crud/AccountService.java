package ro.isdc.auth.service.crud;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.helper.AccountHelper;
import ro.isdc.auth.helper.EntityHelper;
import ro.isdc.auth.repository.AccountRepository;
import ro.isdc.auth.support.ReadOperationParams;
import ro.isdc.auth.support.ReadOperationResults;
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
	public ReadOperationResults read(ReadOperationParams params) throws UnsupportedEncodingException {


		params.setsSearch(URLDecoder.decode(URLEncoder.encode(params.getsSearch(), "ISO-8859-1"),"UTF-8"));
		ReadOperationResults result = new ReadOperationResults();
		Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
		String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
		Sort sort = new Sort(sortDir, sortColName);
		int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

		PageRequest pageReq = new PageRequest(pageNumber, params.getiDisplayLength(), sort);

		List<Account> data;
		Page<Account> page;
		// TODO: To change that
		if (!params.getsSearch().isEmpty()) {
			page = repository.findAllBySearchTerm(params.getsSearch(), pageReq);
		} else {
			page = repository.findAll(pageReq);
		}
		data = page.getContent();
		result.setiTotalDisplayRecords(page.getTotalElements());
		result.setiTotalRecords(page.getTotalElements());
		result.setsEcho(params.getsEcho());
		List<Account> uiDate = new ArrayList<Account>();
		for (Account entity : data) {
			uiDate.add(getHelper().copyFrom(entity));
		}

		result.setAaData(uiDate);
		return result;
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
		if (UserContextUtil.getRoles().contains("ROLE_ADMIN")) {
			accountToSave.setRoles(userAccountToUpdate.getRoles());
		}

		return repository.save(accountToSave);
	}
}
