package rest.app.assignment.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.BookEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long>{
	BookEntity findBybookName(String bookName);
	BookEntity findByBookId(String bookId);
}