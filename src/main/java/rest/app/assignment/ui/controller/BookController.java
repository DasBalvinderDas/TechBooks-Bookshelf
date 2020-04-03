package rest.app.assignment.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.app.assignment.service.BookService;
import rest.app.assignment.shared.dto.BookDto;
import rest.app.assignment.ui.model.request.BookDetailsRequestModel;
import rest.app.assignment.ui.model.response.BookRest;
import rest.app.assignment.ui.model.response.OperationStatusModel;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public BookRest getStatusOfBook(int id) {
		BookRest returnValue = new BookRest();
		return returnValue;
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public BookRest addBook(@RequestBody BookDetailsRequestModel bookDetails) {
		BookRest returnvalue = new BookRest();
		BookDto bookDto = new BookDto();
		
		ModelMapper modelMapper = new ModelMapper();
		bookDto = modelMapper.map(bookDetails, BookDto.class);
		
		bookDto = bookService.addBook(bookDto);
		returnvalue = modelMapper.map(bookDto, BookRest.class);
		
		return returnvalue;
	}

	@DeleteMapping(produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel removeBook(String id) {
		
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("DELETE Book Record");
		operationStatusModel.setOperationResult("SUCCESS");
		return operationStatusModel;
	}
	
	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public BookRest setBookAvailability(String id,boolean available) {
		BookRest returnValue = new BookRest();
		return returnValue;
	}
	
}
