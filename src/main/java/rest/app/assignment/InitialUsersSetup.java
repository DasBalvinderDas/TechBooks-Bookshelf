package rest.app.assignment;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import rest.app.assignment.exceptions.UserServiceException;
import rest.app.assignment.persistence.entity.AuthorityEntity;
import rest.app.assignment.persistence.entity.RoleEntity;
import rest.app.assignment.persistence.entity.UserEntity;
import rest.app.assignment.persistence.repositories.AuthorityRepository;
import rest.app.assignment.persistence.repositories.RoleRepository;
import rest.app.assignment.persistence.repositories.UserRepository;
import rest.app.assignment.shared.Roles;
import rest.app.assignment.shared.Utils;
import rest.app.assignment.ui.model.response.ErrorMessages;

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
			
			AuthorityEntity updateAuthority = createAuthority("UPDATE");
			AuthorityEntity createAuthority = createAuthority("CREATE");
			AuthorityEntity deleteAuthority = createAuthority("DELETE");
			AuthorityEntity viewAuthority = createAuthority("VIEW");
			
			createRole(Roles.ROLE_BORROWER.name(), Arrays.asList(viewAuthority,updateAuthority));
			createRole(Roles.ROLE_LENDER.name(), Arrays.asList(viewAuthority,updateAuthority));
			
			RoleEntity admin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(viewAuthority,updateAuthority,createAuthority,deleteAuthority));
			
			RoleEntity superAdmin = createRole(Roles.ROLE_SUPER.name(), Arrays.asList(viewAuthority,updateAuthority,createAuthority,deleteAuthority));
			
			if(superAdmin == null) return;
			
			UserEntity superAdminUser;
			superAdminUser = userRepository.findByEmail("super@admin.com");
			
			UserEntity adminUser = new UserEntity();
			adminUser = userRepository.findByEmail("admin@test.com");
			
			boolean createSuperAdmin = true;
			boolean createAdmin = true;
			if(null!=superAdminUser) {
				createSuperAdmin = false;
				
				if(null!=adminUser) {
					createAdmin = false;
				}
			}
			
			if(createSuperAdmin)
			createSuperAdminUser(superAdmin);
			
			if(createAdmin)
			createAdminUser(admin);
			
		}

	private void createSuperAdminUser(RoleEntity superAdmin) {
		UserEntity superAdminUser = new UserEntity();
		superAdminUser.setFirstName("root");
		superAdminUser.setLastName("root");
		superAdminUser.setEmail("super@admin.com");
		
		superAdminUser.setUserId(utils.generateUserId(5));
		superAdminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
		superAdminUser.setRoles(Arrays.asList(superAdmin));
		
		userRepository.save(superAdminUser);
	}
	
	private void createAdminUser(RoleEntity admin) {
		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("Balvinder");
		adminUser.setLastName("Das");
		adminUser.setEmail("admin@test.com");
		
		adminUser.setUserId(utils.generateUserId(5));
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
		adminUser.setRoles(Arrays.asList(admin));
		
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

		RoleEntity role = new RoleEntity();
		try{
	        role = roleRepository.findByName(name);
	        if (role == null) {
	            role = new RoleEntity(name);
	            role.setAuthorities(authorities);
	            roleRepository.save(role);
	        }
		} catch (Exception ex) {
			throw new UserServiceException(ErrorMessages.COULD_NOT_CREATE_NEW_ROLE_IN_THE_SYSTEM.getErrorMessage());
		}
        return role;
    }
	
}