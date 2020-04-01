package rest.app.assignment.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import rest.app.assignment.persistence.entity.BookEntity;

public interface BookRepository extends CrudRepository<BookEntity, Long>{
	
}