package rest.app.assignment.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import rest.app.assignment.persistence.entity.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Long>{

}
