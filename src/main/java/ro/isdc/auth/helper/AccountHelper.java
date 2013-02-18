package ro.isdc.auth.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.domain.Role;
import ro.isdc.auth.repository.AccountRepository;
import ro.isdc.auth.security.UserContext;
import ro.isdc.auth.support.UserContextUtil;
import ro.isdc.auth.support.ValueGenerator;

/**
 * Account Helper
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */
@Component
public class AccountHelper implements EntityHelper<Account> {

	private static final String PASSWORD_UI = "**********";
	private @Autowired
	AccountRepository userRepository;

	@Override
	public Account copyFrom(final Account entity) {
		Account copy = new Account();
		copy.setId(entity.getId());
		copy.setFirstName(entity.getFirstName());
		copy.setLastName(entity.getLastName());
		copy.setEmail(entity.getEmail());
		copy.setPassword(PASSWORD_UI);
		copy.setIsEnabled(entity.getIsEnabled());
		copy.setRoles(entity.getRoles());
		return copy;
	}

	@Override
	public Account copyWithoutPkFrom(final Account entity) {
		Account copy = new Account();
		copy.setFirstName(entity.getFirstName());
		copy.setLastName(entity.getLastName());
		copy.setEmail(entity.getEmail());
		copy.setPassword(PASSWORD_UI);
		copy.setIsEnabled(entity.getIsEnabled());
		return copy;
	}

	@Override
	public Account createEntityInstance() {
		return new Account();
	}

	@Override
	public Account createRandomEntity() {
		Account account = new Account();
		account.setFirstName(ValueGenerator.getMaxString(100));
		account.setLastName(ValueGenerator.getMaxString(100));
		account.setEmail(ValueGenerator.getUniqueEmail());
		account.setPassword(PASSWORD_UI);
		account.setIsEnabled(true);
		return account;
	}

	@Override
	public Account updateFrom(final Account fromEntity, Account toEntity) {
		toEntity.setFirstName(fromEntity.getFirstName());
		toEntity.setLastName(fromEntity.getLastName());
		if (!fromEntity.getPassword().equals(PASSWORD_UI)) {
			toEntity.setPassword(DigestUtils.md5Hex(fromEntity.getPassword()));
		}
		if (UserContextUtil.getRoles().contains("ROLE_ADMIN")) {
			toEntity.setIsEnabled(fromEntity.getIsEnabled());
			toEntity.setRoles(fromEntity.getRoles());
		} else {

			// TODO: see if we can do this better
			Account user = userRepository.findOne(UserContext.getUsername());
			toEntity.setIsEnabled(user.getIsEnabled());
			toEntity.setRoles(user.getRoles());
		}
		return toEntity;
	}

	/**
	 * Getting the account role names.
	 * 
	 * @param account
	 * 
	 * @return list of role names
	 */
	public List<String> getRoleNames(final Account account) {
		List<String> roleNames = new ArrayList<String>();

		for (Role role : account.getRoles()) {
			roleNames.add(role.getRoleName());
		}
		return roleNames;
	}

}
