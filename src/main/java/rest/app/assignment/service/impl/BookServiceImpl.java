package rest.app.assignment.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.app.assignment.exceptions.BookServiceException;
import rest.app.assignment.persistence.entity.BookEntity;
import rest.app.assignment.persistence.repositories.BookRepository;
import rest.app.assignment.service.BookService;
import rest.app.assignment.shared.Utils;
import rest.app.assignment.shared.dto.BookDto;
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
		
		bookEntity.setBookId(utils.generateBookId(5));
		bookEntity.setLastUpdated(new Date());
		bookEntity.setLender(utils.getLoginUserEmail());
		
		bookRepository.save(bookEntity);
		
		BookDto returnValue = modelMapper.map(bookEntity, BookDto.class);

		return returnValue;
		
	}
	
	@Override
	public List<BookDto> addBooks(List<BookDto> lstBookDto) {
		
		BookEntity storedBookDetails;
		String publicBookId;
		String email = utils.getLoginUserEmail();
		List<BookEntity> lstBookEntity = new ArrayList<BookEntity>();
		List<BookDto> returnValue = new ArrayList<BookDto>();
		
		for (BookDto bookDto : lstBookDto) {
			storedBookDetails = bookRepository.findBybookName(bookDto.getBookName());

			if (storedBookDetails != null)
				throw new RuntimeException("In bulk upload no book can pre exist in the system");
			
			publicBookId = utils.generateBookId(5);
			bookDto.setBookId(publicBookId);
			bookDto.setLastUpdated(new Date());
			bookDto.setLender(email);
			
		}
		
		if(null!=lstBookDto && !lstBookDto.isEmpty()) {
			Type listType = new TypeToken<List<BookEntity>>() {}.getType();
			lstBookEntity = new ModelMapper().map(lstBookDto, listType);
		}
		
		if(null==lstBookEntity) return returnValue;
		
		Iterable<BookEntity> savedBooks = bookRepository.saveAll(lstBookEntity);
		
		for (BookEntity bookEntity : savedBooks) {
			returnValue.add(new ModelMapper().map(bookEntity, BookDto.class));
		}
		
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
		
		BeanUtils.copyProperties(bookDto, bookEntity);

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
	public List<BookDto> getAllBooks() {
		List<BookDto> returnValue = new ArrayList<BookDto>();
		
		Iterable<BookEntity> books = bookRepository.findAll();
		for (BookEntity bookEntity : books) {
			returnValue.add(new ModelMapper().map(bookEntity, BookDto.class));
		}
		return returnValue;
	}

	
}
