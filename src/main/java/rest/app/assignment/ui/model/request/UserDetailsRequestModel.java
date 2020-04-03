package rest.app.assignment.ui.model.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsRequestModel {

	@NotNull(message="first name can not be empty")
	@Size(min=2,max=30,message="firstName can not be more then 30 and less then 2 characters")
	private String firstName;
	
	@NotNull(message="last name can not be empty")
	@Size(min=2,max=30,message="lastName can not be more then 30 and less then 2 characters")
	private String lastName;
	
	@NotNull(message="email can not be empty")
	@Email
	private String email;
	
	@JsonProperty
	@NotNull(message="isActive is required value")
	private boolean isActive;
	
	@Size(min=3,max=16,message="password can not be more then 16 and less then 3 characters")
	@NotNull(message="password can not be empty")
	private String password;
	
	@JsonProperty
	@NotNull(message="isLender is required value")
	private boolean isLender;

	private List<AddressRequestModel> addresses;

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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLender() {
		return isLender;
	}

	public void setLender(boolean isLender) {
		this.isLender = isLender;
	}

	public List<AddressRequestModel> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressRequestModel> addresses) {
		this.addresses = addresses;
	}

}
