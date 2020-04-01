package rest.app.assignment.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.app.assignment.persistence.entity.BookEntity;
import rest.app.assignment.persistence.entity.UserEntity;
import rest.app.assignment.persistence.repositories.BookRepository;
import rest.app.assignment.service.BookService;
import rest.app.assignment.shared.Utils;
import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.shared.dto.UserDto;

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

}
