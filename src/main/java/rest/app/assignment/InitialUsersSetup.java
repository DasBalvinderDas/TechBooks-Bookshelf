package rest.app.assignment;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import rest.app.assignment.persistence.entity.AuthorityEntity;
import rest.app.assignment.persistence.entity.RoleEntity;
import rest.app.assignment.persistence.entity.UserEntity;
import rest.app.assignment.persistence.repositories.AuthorityRepository;
import rest.app.assignment.persistence.repositories.RoleRepository;
import rest.app.assignment.persistence.repositories.UserRepository;
import rest.app.assignment.shared.Roles;
import rest.app.assignment.shared.Utils;

@Component
public class InitialUsersSetup {
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Utils utils;
	
	@Autowired
	UserRepository userRepository;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority,writeAuthority));
		RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority,writeAuthority, deleteAuthority));
		
		if(roleAdmin == null) return;
		
		UserEntity adminUser = new UserEntity();
		adminUser = userRepository.findByEmail("admin@test.com");
		
		
		if(null!=adminUser) return;
		
		adminUser.setFirstName("Balvinder");
		adminUser.setLastName("Das");
		adminUser.setEmail("admin@test.com");
		
		adminUser.setUserId(utils.generateUserId(5));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
		adminUser.setRoles(Arrays.asList(roleAdmin));
		
		userRepository.save(adminUser);
		
	}
	
	@Transactional
    private AuthorityEntity createAuthority(String name) {

        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }
	
	@Transactional
    private RoleEntity createRole(
            String name, Collection<AuthorityEntity> authorities) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
	
}