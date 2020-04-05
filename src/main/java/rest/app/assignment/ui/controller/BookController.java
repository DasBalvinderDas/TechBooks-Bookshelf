package rest.app.assignment.ui.controller;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.app.assignment.persistence.entity.BookEntity;
import rest.app.assignment.security.UserPrincipal;
import rest.app.assignment.service.BookService;
import rest.app.assignment.service.UserService;
import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.request.BookDetailsRequestModel;
import rest.app.assignment.ui.model.response.BookRest;
import rest.app.assignment.ui.model.response.OperationStatusModel;
import rest.app.assignment.ui.model.response.UserRest;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	UserService userService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public ResponseEntity<BookRest> addBook(@RequestBody BookDetailsRequestModel bookDetails) {
		BookRest returnvalue = new BookRest();
		BookDto bookDto = new BookDto();
		
		ModelMapper modelMapper = new ModelMapper();
		bookDto = modelMapper.map(bookDetails, BookDto.class);
		
		bookDto.setLastUpdated(new Date());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = "";
		if (principal instanceof UserPrincipal) {
		   email = ((UserPrincipal) principal).getEmail();
		}
		bookDto.setLender(email);
		
		bookDto = bookService.addBook(bookDto);
		returnvalue = modelMapper.map(bookDto, BookRest.class);
		return new ResponseEntity<BookRest>(returnvalue,HttpStatus.OK);
	}

	@PutMapping(path = "/assign/{bookId}/{borrowerId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public ResponseEntity<OperationStatusModel> assignBook(@PathVariable String bookId,@PathVariable String borrowerId ) {
		
		UserDto userDto = new UserDto();
		userDto = userService.getUserByUserId(borrowerId);
		
		BookDto bookDto = bookService.getBookById(bookId); 
		bookDto.setLastUpdated(new Date());
		bookDto.setBorrower(userDto.getEmail());
		
		OperationStatusModel operationStatusModel = bookService.assignBook(bookDto);
		
		return new ResponseEntity<OperationStatusModel>(operationStatusModel,HttpStatus.OK);
		
	}
	
	@GetMapping(path = "/{id}" ,produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public BookRest getStatusOfBook(int id) {
		BookRest returnValue = new BookRest();
		return returnValue;
	}
	
	@DeleteMapping(produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public OperationStatusModel removeBook(String id) {
		
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("DELETE Book Record");
		operationStatusModel.setOperationResult("SUCCESS");
		return operationStatusModel;
	}
	
	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public BookRest setBookAvailability(String id,boolean available) {
		BookRest returnValue = new BookRest();
		return returnValue;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public UserRest getAllBooks(@PathVariable String id) {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return userRest;
	}
	
	@GetMapping(path = "/{id}/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public UserRest getBookById(@PathVariable String id) {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return userRest;
	}
	
	@GetMapping(path = "/lender/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public UserRest getAllBooksProvidedByLender(@PathVariable String id) {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return userRest;
	}
	
	
}
