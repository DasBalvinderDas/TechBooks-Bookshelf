package rest.app.assignment.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
	
	@Query(value="SELECT * FROM USERS U WHERE U.ID :userId",nativeQuery=true)
	List<UserEntity> findAllUsersById(@Param("userId") long id);

	@Query(value="SELECT * FROM USERS_ROLES WHERE ROLES_ID :roleId",nativeQuery=true)
	List<Object> getUserIdForRoleId(@Param("roleId") String id);
	
}