package rest.app.assignment.persistence.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.AuthorityEntity;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
	AuthorityEntity findByName(String name);
}