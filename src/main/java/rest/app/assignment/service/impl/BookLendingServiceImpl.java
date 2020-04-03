package rest.app.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.app.assignment.persistence.repositories.BookKeepingRecordRepository;
import rest.app.assignment.service.BookLendingService;
import rest.app.assignment.shared.dto.BookLendingDto;

@Service
public class BookLendingServiceImpl implements BookLendingService{

	@Autowired
	BookKeepingRecordRepository bookKeepingRecordRepository;
	
	@Override
	public BookLendingDto getLenderDetail(String email) {
		return null;
	}

}
