package rest.app.assignment.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.app.assignment.service.BookLendingService;
import rest.app.assignment.service.BookService;
import rest.app.assignment.ui.model.request.BookDetailsRequestModel;
import rest.app.assignment.ui.model.response.BookLendingRest;

@RestController
@RequestMapping("status")
public class BookKeepingController {
	
	@Autowired
	BookLendingService bookLendingService;

	@GetMapping(value="/borrower",produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public BookLendingRest getBorrowereDtail(String email) {
		BookLendingRest returnValue = new BookLendingRest();
		return returnValue;
	}
	
	@GetMapping(value="/lender",produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public BookLendingRest getLenderDetail(String email){
		BookLendingRest returnValue = new BookLendingRest();
		
		bookLendingService.getLenderDetail(email);
		
		return returnValue;
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public BookLendingRest addBook(@RequestBody BookDetailsRequestModel bookDetails) {
		BookLendingRest returnValue = new BookLendingRest();
		return returnValue;
	}
	
	@PutMapping( produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public BookLendingRest makeUserLender(String email,boolean flag) {
		BookLendingRest returnValue = new BookLendingRest();
		return returnValue;
	}
	
}
