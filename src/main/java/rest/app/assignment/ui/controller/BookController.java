package rest.app.assignment.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.app.assignment.exceptions.BookServiceException;
import rest.app.assignment.service.BookService;
import rest.app.assignment.service.UserService;
import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.request.BookDetailsRequestModel;
import rest.app.assignment.ui.model.response.BookRest;
import rest.app.assignment.ui.model.response.OperationStatusModel;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookRest> addBook(@RequestBody BookDetailsRequestModel bookDetails) {
		BookRest returnvalue = new BookRest();
		BookDto bookDto = new BookDto();
		
		ModelMapper modelMapper = new ModelMapper();
		bookDto = modelMapper.map(bookDetails, BookDto.class);
		
		bookDto = bookService.addBook(bookDto);
		returnvalue = modelMapper.map(bookDto, BookRest.class);
		return new ResponseEntity<BookRest>(returnvalue,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@PostMapping(value="/multiple",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BookRest>> addBooks(@RequestBody List<BookDetailsRequestModel> multipleBookDetails) {
		
		
		List<BookDto> lstBookDto = new ArrayList<BookDto>();
		List<BookRest> lstBookRest = new ArrayList<BookRest>();
		
		if(null!=multipleBookDetails && !multipleBookDetails.isEmpty()) {
			Type listType = new TypeToken<List<BookDto>>() {}.getType();
			lstBookDto = new ModelMapper().map(multipleBookDetails, listType);
		}
		
		lstBookDto = bookService.addBooks(lstBookDto);
		
		return new ResponseEntity<List<BookRest>>(lstBookRest,HttpStatus.OK);
	}


	@PutMapping(path = "/{bookId}/{borrowerId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OperationStatusModel> assignBook(@PathVariable String bookId,@PathVariable String borrowerId ) {
		
		UserDto userDto = new UserDto();
		userDto = userService.getUserByUserId(borrowerId);
		
		BookDto bookDto = bookService.getBookById(bookId); 
		bookDto.setLastUpdated(new Date());
		bookDto.setAvailable(false);
		bookDto.setBorrower(userDto.getEmail());
		
		OperationStatusModel operationStatusModel = bookService.assignBook(bookDto);
		
		return new ResponseEntity<OperationStatusModel>(operationStatusModel,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@GetMapping(path = "/status/{bookId}" ,produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookRest> getStatusOfBook(@PathVariable String bookId) {
		BookDto bookDto = bookService.getBookById(bookId); 
		ModelMapper modelMapper = new ModelMapper();
		
		BookRest returnvalue = modelMapper.map(bookDto, BookRest.class);
		return new ResponseEntity<BookRest>(returnvalue,HttpStatus.OK);

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/{bookId}",produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel removeBook(@PathVariable String bookId) {
		
		bookService.deleteBook(bookId);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("DELETE Book Record");
		operationStatusModel.setOperationResult("SUCCESS");
		return operationStatusModel;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/{bookId}/{isAvailable}", produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookRest> makeBookActive(@PathVariable String bookId,@PathVariable boolean isActive) {
		
		BookDto bookDto = bookService.getBookById(bookId); 
		
		if(false == bookDto.isAvailable()) {
			throw new BookServiceException("can't deactivate an assign book");
		}
		
		bookDto.setActive(isActive);
		
		BookRest returnValue = bookService.update(bookDto);
		return new ResponseEntity<BookRest>(returnValue,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path="/all",produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BookRest>> getAllBooks(@PathVariable String id) {
		
		List<BookRest> returnvalue = new ArrayList<BookRest>();
		List<BookDto> lstBookDto = bookService.getAllBooks();

		if(null!=lstBookDto && !lstBookDto.isEmpty()) {
			Type listType = new TypeToken<List<BookRest>>() {}.getType();
			returnvalue = new ModelMapper().map(lstBookDto, listType);
		}
		return new ResponseEntity<List<BookRest>>(returnvalue,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookRest> getBookById(@PathVariable String id) {
		BookRest bookRest = new BookRest();
		BookDto bookDto = bookService.getBookById(id);
		ModelMapper modelMapper = new ModelMapper();
		bookRest = modelMapper.map(bookDto, BookRest.class);
		return new ResponseEntity<BookRest>(bookRest,HttpStatus.OK);
	}
	
	
}