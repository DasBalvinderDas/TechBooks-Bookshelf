package rest.app.assignment.service;

import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.ui.model.response.OperationStatusModel;

public interface BookService {

	public BookDto addBook(BookDto bookDto);

	public BookDto getBookById(String bookId);

	public OperationStatusModel assignBook(BookDto bookDto);

}
