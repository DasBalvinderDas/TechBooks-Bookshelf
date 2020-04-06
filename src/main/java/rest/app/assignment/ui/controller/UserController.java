package rest.app.assignment.ui.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
import rest.app.assignment.ui.model.request.UserDetailsUpdateModel;
import rest.app.assignment.ui.model.response.OperationStatusModel;
import rest.app.assignment.ui.model.response.UserRest;

@RestController

public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER')")
	@PostMapping(value="/users",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
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
	
	@PreAuthorize("hasRole('SUPER')")
	@PutMapping(path = "/users/admin/{id}",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> makeAdmin(@PathVariable String id) {

		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		userDto = userService.getUserByUserId(id);
		
		//setting role in the user
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_ADMIN.name())));
		
		UserDto updatedUser = userService.updateUser(id, userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(updatedUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SUPER')")
	@DeleteMapping(path = "/users/admin/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OperationStatusModel> deleteAdmin(@PathVariable String id) {

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		boolean isUserAdmin = false;
		UserDto userDto = new UserDto();
		
		userDto = userService.getUserByUserId(id);
		
		Iterator<String> itr = userDto.getRoles().iterator();
		String role;
		while(itr.hasNext()) {
			role = itr.next();
			if(role.equalsIgnoreCase("ROLE_ADMIN")) {
				isUserAdmin = true;
				break;
			}
		}
		if(isUserAdmin) {
			userService.deleteUser(id);

			operationStatusModel = new OperationStatusModel();
			operationStatusModel.setOperationName("DELETE User Record");
			operationStatusModel.setOperationResult("SUCCESS");
			
		}
		return new ResponseEntity<OperationStatusModel>(operationStatusModel,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/users/lender/{id}",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> makeLender(@PathVariable String id) {

		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		
		userDto = userService.getUserByUserId(id);
		//setting role in the user
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_LENDER.name())));
		
		UserDto createdUser = userService.updateUser(id, userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(createdUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@GetMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> getUser(@PathVariable String id) {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or #id == principal.userId")
	@PutMapping(path = "/users/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> updateUser(@Valid @RequestBody UserDetailsUpdateModel userDetails , @PathVariable String id) {
		UserDto userDto = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		
		userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);

		UserRest userRest = new UserRest();
		userRest = modelMapper.map(updatedUser, UserRest.class);

		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OperationStatusModel> deleteUser(@PathVariable String id) {

		userService.deleteUser(id);
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName("DELETE User Record");
		operationStatusModel.setOperationResult("SUCCESS");
		return new ResponseEntity<OperationStatusModel>(operationStatusModel,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@GetMapping(path = "/users/lenders", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> showAllLenders() {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getAllLenderRoleUsers();
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('BORROWER')")
	@GetMapping(path = "/users/borrowers", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> showAllBorrowers() {
		UserRest userRest = new UserRest();
		UserDto userDto = userService.getAllBorrowerRoleUsers();
		ModelMapper modelMapper = new ModelMapper();
		userRest = modelMapper.map(userDto, UserRest.class);
		return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	}
	
}
