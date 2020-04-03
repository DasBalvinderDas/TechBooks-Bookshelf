package rest.app.assignment.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lendingrecords")
public class BookKeepingRecordEntity implements Serializable{

	
	private static final long serialVersionUID = 3086021117682966940L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private BookEntity book;
	
	@Column(nullable = false)
	private UserEntity borrower;
	
	@Column(nullable = false)
	private UserEntity lender;
	
	@Column(nullable = false)
	private Date issueDate;
	
	@Column(nullable = false)
	private Date submissionDate;
	
	@Column(nullable = false)
	private Date actualSubmissionDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public UserEntity getBorrower() {
		return borrower;
	}

	public void setBorrower(UserEntity borrower) {
		this.borrower = borrower;
	}

	public UserEntity getLender() {
		return lender;
	}

	public void setLender(UserEntity lender) {
		this.lender = lender;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getActualSubmissionDate() {
		return actualSubmissionDate;
	}

	public void setActualSubmissionDate(Date actualSubmissionDate) {
		this.actualSubmissionDate = actualSubmissionDate;
	}

}
