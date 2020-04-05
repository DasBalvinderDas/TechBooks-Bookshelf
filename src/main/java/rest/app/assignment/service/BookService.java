package rest.app.assignment.service;

import java.util.List;

import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.response.BookRest;
import rest.app.assignment.ui.model.response.OperationStatusModel;

public interface BookService {

	public BookDto addBook(BookDto bookDto);

	public BookDto getBookById(String bookId);

	public OperationStatusModel assignBook(BookDto bookDto);

	public void deleteBook(String bookId);

	public BookRest update(BookDto bookDto);

	public List<UserDto> getAllBooks();

}
