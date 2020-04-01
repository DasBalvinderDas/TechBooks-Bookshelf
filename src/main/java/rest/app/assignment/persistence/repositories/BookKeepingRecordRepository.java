package rest.app.assignment.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import rest.app.assignment.persistence.entity.BookKeepingRecordEntity;

public interface BookKeepingRecordRepository extends CrudRepository<BookKeepingRecordEntity, Long>{

}
