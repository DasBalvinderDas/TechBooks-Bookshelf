package rest.app.assignment.shared.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 300780921650980195L;

	private String id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private boolean isActive = true;
	private boolean isLender = false;
	private List<AddressDto> addresses;
	private Collection<String> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isLender() {
		return isLender;
	}

	public void setLender(boolean isLender) {
		this.isLender = isLender;
	}

	public List<AddressDto> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDto> addresses) {
		this.addresses = addresses;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

}
