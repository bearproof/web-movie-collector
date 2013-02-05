package ro.isdc.auth.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.isdc.auth.repository.AccountRepository;
import ro.isdc.auth.repository.RoleRepository;


/**
 * A general purpose controller used to validate fields on a form.
 * 
 * @author maachou
 */
@Controller
@RequestMapping("/validator/*")
public class CommonValidationController {
    private static final Logger logger = Logger.getLogger(CommonValidationController.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    /**
     * Checking the existency of an email
     * 
     * @param email
     * @return "true" or "false"
     */
    @RequestMapping(value = "/checkemail", params = "email")
    public @ResponseBody
    String checkMail(@RequestParam String email) {
	logger.debug("checking email : " + email);
	return (accountRepository.findByEmail(email) != null) ? "false" : "true";
    }

    /**
     * Checking the existency of a role name
     * 
     * @param roleName
     * @return "true" or "false"
     */
    @RequestMapping(value = "/checkrolename", params = "roleName")
    public @ResponseBody
    String checkRoleName(@RequestParam String roleName) {
	logger.debug("checking role name : " + roleName);
	return (roleRepository.findByRoleName(roleName) != null) ? "false" : "true";
    }
}
