package rest.app.assignment.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rest.app.assignment.exceptions.BookServiceException;
import rest.app.assignment.persistence.entity.BookEntity;
import rest.app.assignment.persistence.entity.UserEntity;
import rest.app.assignment.persistence.repositories.BookRepository;
import rest.app.assignment.service.BookService;
import rest.app.assignment.shared.Utils;
import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.response.BookRest;
import rest.app.assignment.ui.model.response.OperationStatusModel;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepository bookRepository;
	

	
	@Autowired
	Utils utils;
	
	@Override
	public BookDto addBook(BookDto bookDto) {
		
		BookEntity storedBookDetails = bookRepository.findBybookName(bookDto.getBookName());

		if (storedBookDetails != null)
			throw new RuntimeException("Record Already exists");
		
		ModelMapper modelMapper = new ModelMapper();
		BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);
		
		String publicBookId = utils.generateBookId(5);
		bookEntity.setBookId(publicBookId);
		
		bookRepository.save(bookEntity);
		
		BookDto returnValue = modelMapper.map(bookEntity, BookDto.class);

		return returnValue;
		
	}

	@Override
	public BookDto getBookById(String bookId) {
		BookEntity bookEntity =  bookRepository.findByBookId(bookId);
		ModelMapper modelMapper = new ModelMapper();
		BookDto returnValue =  modelMapper.map(bookEntity, BookDto.class);
		
		return returnValue;
	}
	
	@Override
	public OperationStatusModel assignBook(BookDto bookDto) {
		
		
		ModelMapper modelMapper = new ModelMapper();
		BookEntity bookEntity = bookRepository.findByBookId(bookDto.getBookId());
		if (null == bookEntity)
			throw new BookServiceException(String.valueOf(bookDto.getBookId()));
		
		bookEntity = modelMapper.map(bookDto, BookEntity.class);

		bookRepository.save(bookEntity);
		
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("Assign Boook");
		operationStatusModel.setOperationResult("SUCCESS");

		return operationStatusModel;
	}

	@Override
	public void deleteBook(String bookId) {
		BookEntity bookEntity = bookRepository.findByBookId(bookId);
		if (null == bookEntity)
			throw new BookServiceException("not able to delete this book " + bookId);

		bookRepository.delete(bookEntity);
	}
	
	@Override
	public BookRest update(BookDto bookDto) {
		
		ModelMapper modelMapper = new ModelMapper();
		BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);
		
		bookEntity = bookRepository.save(bookEntity);
		BookRest returnValue = modelMapper.map(bookEntity, BookRest.class);
		
		return returnValue;
	}

	@Override
	public List<UserDto> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

}
