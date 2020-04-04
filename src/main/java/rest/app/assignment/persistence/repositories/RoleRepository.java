package rest.app.assignment.persistence.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
	RoleEntity findByName(String name);
}