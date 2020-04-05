package rest.app.assignment.ui.controller;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.app.assignment.service.AddressService;
import rest.app.assignment.service.UserService;
import rest.app.assignment.shared.Roles;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.request.UserDetailsRequestModel;
import rest.app.assignment.ui.model.response.OperationStatusModel;
import rest.app.assignment.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER')")
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {

		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		userDto = modelMapper.map(userDetails, UserDto.class);
		
		//setting role in the user
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_BORROWER.name())));
		
		UserDto createdUser = userService.createUser(userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(createdUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('SUPER')")
	public ResponseEntity<UserRest> makeAdmin(@Valid @RequestBody UserDetailsRequestModel userDetails) {

		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		userDto = modelMapper.map(userDetails, UserDto.class);
		
		//setting role in the user
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_BORROWER.name())));
		
		UserDto createdUser = userService.createUser(userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(createdUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@DeleteMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('SUPER')")
	public ResponseEntity<UserRest> deleteAdmin(@Valid @RequestBody UserDetailsRequestModel userDetails) {

		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		userDto = modelMapper.map(userDetails, UserDto.class);
		
		//setting role in the user
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_BORROWER.name())));
		
		UserDto createdUser = userService.createUser(userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(createdUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserRest> makeLender(@Valid @RequestBody UserDetailsRequestModel userDetails) {

		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		userDto = modelMapper.map(userDetails, UserDto.class);
		
		//setting role in the user
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_BORROWER.name())));
		
		UserDto createdUser = userService.createUser(userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(createdUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public UserRest getUser(@PathVariable String id) {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return userRest;
	}

	
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or #id == principal.userId")
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.updateUser(id, userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(createdUser, UserRest.class);

		return userRest;
	}
	
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	public OperationStatusModel deleteUser(@PathVariable String id) {

		userService.deleteUser(id);

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("DELETE User Record");
		operationStatusModel.setOperationResult("SUCCESS");
		return operationStatusModel;
	}

	
	
}
