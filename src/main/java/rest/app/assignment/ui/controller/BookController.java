package rest.app.assignment.ui.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import rest.app.assignment.service.UserService;


public class BookController {

	@Autowired
	UserService userService;

	@GetMapping
	public String getStatusOfAllBooks() {
		return "get user is called";
	}

	@GetMapping
	public String getStatusOFBook(int id) {
		return "get user is called";
	}

	@GetMapping
	public String getStatusOFBook(String name) {
		return "get user is called";
	}

	
	public void addBooks() {
		
	}

	public void addBook() {
		
	}

	public void removeAllBooks() {

	}

	public void removeBook(long id) {

	}
	
	public void removeBook(String name) {

	}

	public void makeAllBooksAvailable() {

	}

	public void makeAllBooksNonAvailable() {

	}

	public void makeBookOffShelf(String name) {

	}

	public void makeBookOffShelf(long id) {

	}


}
