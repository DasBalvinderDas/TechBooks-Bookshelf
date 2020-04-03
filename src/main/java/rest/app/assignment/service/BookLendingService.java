package rest.app.assignment.service;

import rest.app.assignment.shared.dto.BookLendingDto;

public interface BookLendingService {

	BookLendingDto getLenderDetail(String email);

}
