package rest.app.assignment.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rest.app.assignment.persistence.entity.BookKeepingRecordEntity;

@Repository
public interface BookKeepingRecordRepository extends CrudRepository<BookKeepingRecordEntity, Long>{

	BookKeepingRecordEntity findByLender(String email);

}
