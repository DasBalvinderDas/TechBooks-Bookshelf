package rest.app.assignment.ui.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsRequestModel {

	private String firstName;
	private String lastName;
	private String email;
	@JsonProperty
	private boolean isActive;
	private String userName;
	private String password;
	@JsonProperty
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
