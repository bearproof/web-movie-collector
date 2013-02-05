package ro.isdc.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.isdc.auth.domain.Role;

/**
 * Account repository
 * 
 * @author maachou
 * @author Adrian.Ursu
 * 
 */
public interface RoleRepository extends MongoRepository<Role, String> {

	Role findByRoleName(String roleName);
}
