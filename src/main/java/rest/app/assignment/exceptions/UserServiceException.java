package rest.app.assignment.exceptions;
public class UserServiceException extends RuntimeException{
 
	private static final long serialVersionUID = 4238099146594016553L;

	public UserServiceException(String message)
	{
		super(message);
	}
}