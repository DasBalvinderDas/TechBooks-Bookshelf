package rest.app.assignment.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import rest.app.assignment.exceptions.UserServiceException;
import rest.app.assignment.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	public UserDto createUser(UserDto userDto);
	public UserDto getUser(String email) ;
	public UserDto getUserByUserId(String id);
	public UserDto updateUser(String id, UserDto userDto) ;
	public void deleteUser(String id) ;
	
}
