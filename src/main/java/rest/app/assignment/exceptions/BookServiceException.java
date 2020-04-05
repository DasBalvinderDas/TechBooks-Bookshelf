package rest.app.assignment.exceptions;
public class BookServiceException extends RuntimeException{
 
	private static final long serialVersionUID = 4238099146594016553L;

	public BookServiceException(String message)
	{
		super(message);
	}
}