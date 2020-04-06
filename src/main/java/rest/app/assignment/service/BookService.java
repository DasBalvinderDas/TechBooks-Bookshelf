package rest.app.assignment.service;

import java.util.List;

import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.response.BookRest;
import rest.app.assignment.ui.model.response.OperationStatusModel;

public interface BookService {

	public BookDto addBook(BookDto bookDto);
	
	public List<BookDto> addBooks(List<BookDto> lstBookDto);

	public BookDto getBookById(String bookId);
	
	public List<BookDto> getAllBooks();

	public OperationStatusModel assignBook(BookDto bookDto);

	public void deleteBook(String bookId);

	public BookRest update(BookDto bookDto);

}
