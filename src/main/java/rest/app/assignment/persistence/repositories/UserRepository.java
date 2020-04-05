package rest.app.assignment.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
	
	@Query(value="select * from users ",nativeQuery=true)
	UserEntity findAllLenderRoleUsers();

	@Query(value="select * from users ",nativeQuery=true)
	UserEntity findAllBorrowerRoleUsers();
	
}