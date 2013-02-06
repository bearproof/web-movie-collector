package ro.isdc.auth.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.legacyprofile.LegacyGoogleProfile;

import ro.isdc.auth.domain.Account;
import ro.isdc.auth.domain.Role;
import ro.isdc.auth.repository.AccountRepository;
import ro.isdc.auth.repository.RoleRepository;

public class AccountConnectionSignUp implements ConnectionSignUp {

	private @Autowired
	AccountRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public String execute(Connection<?> connection) {

		Account account = new Account();

		Role userRole = getRole("ROLE_USER");

		if (connection.getKey().getProviderId().equalsIgnoreCase("facebook")) {
			// TODO: Double check this in the flow

			UserProfile profile = connection.fetchUserProfile();

			account.setEmail(profile.getEmail());
			account.setFirstName(profile.getFirstName());
			account.setLastName(profile.getLastName());
			account.setIsEnabled(true);
			account.setPassword(DigestUtils.md5Hex("?#XLRUF.4^#r##$m^o"));
			account.getRoles().add(userRole);
		} else if (connection.getKey().getProviderId().equalsIgnoreCase("google")) {
			// To populate an account from the google profile
			Google google = (Google) connection.getApi();
			LegacyGoogleProfile profile = google.userOperations().getUserProfile();
			account.setEmail(profile.getEmail());
			account.setFirstName(profile.getFirstName());
			account.setLastName(profile.getLastName());
			account.setPassword(DigestUtils.md5Hex("?#XLRUF.4^#r##$m^o"));
			account.setIsEnabled(true);
			account.getRoles().add(userRole);
		}

		userRepository.save(account);
		return account.getId();
	}

	private Role getRole(final String roleId) {
		Role result = roleRepository.findByRoleName(roleId);
		if (result == null) {
			result = new Role();
			result.setRoleName(roleId);
			roleRepository.save(result);
		}
		return result;
	}

}
