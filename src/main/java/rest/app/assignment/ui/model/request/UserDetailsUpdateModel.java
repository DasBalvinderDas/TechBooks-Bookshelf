package rest.app.assignment.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsUpdateModel {

	@NotNull(message="first name can not be empty")
	@Size(min=2,max=30,message="firstName can not be more then 30 and less then 2 characters")
	private String firstName;
	
	@NotNull(message="last name can not be empty")
	@Size(min=2,max=30,message="lastName can not be more then 30 and less then 2 characters")
	private String lastName;
	
	@NotNull(message="email can not be empty")
	@Email
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
