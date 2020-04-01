package rest.app.assignment.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "book")
public class BookEntity implements Serializable{


	private static final long serialVersionUID = -7177397966710228721L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String bookId;

	@Column(nullable = false, length = 50)
	private String bookname;
	
	@Column(nullable = false, length = 50)
	private String publisherName;
	
	@Column(nullable = false, length = 50)
	private String isbn;
	
	@Column(nullable = false, length = 50)
	private String description;
	
	@Column(nullable = false, length = 50)
	private String bookAvailable;
	
	@Column(nullable = false, length = 50)
	private String bookActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBookAvailable() {
		return bookAvailable;
	}

	public void setBookAvailable(String bookAvailable) {
		this.bookAvailable = bookAvailable;
	}

	public String getBookActive() {
		return bookActive;
	}

	public void setBookActive(String bookActive) {
		this.bookActive = bookActive;
	}
	
}
