package rest.app.assignment.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
	
	@Query(value="SELECT * FROM USERS U, ROLES R , USERS_ROLES UR WHERE U.ID = UR.USERS_ID AND R.ID = UR.ROLES_ID AND R.NAME  = 'ROLE_LENDER'; ",nativeQuery=true)
	UserEntity findAllLenderRoleUsers();

	@Query(value="SELECT * FROM USERS U, ROLES R , USERS_ROLES UR WHERE U.ID = UR.USERS_ID AND R.ID = UR.ROLES_ID AND R.NAME  = 'ROLE_BORROWER'; ",nativeQuery=true)
	UserEntity findAllBorrowerRoleUsers();
	
}