package rest.app.assignment.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import rest.app.assignment.service.AddressService;
import rest.app.assignment.shared.dto.AddressDto;
import rest.app.assignment.ui.model.request.AddressRequestModel;
import rest.app.assignment.ui.model.response.AddressRest;

@RestController

public class AddressController {

	@Autowired
	AddressService addressService;

	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization", value="Book JWT Token",paramType = "header")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@GetMapping(path = "/{userId}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<AddressRest>> getAddresses(@PathVariable String userId) {
		List<AddressRest> returnvalue = new ArrayList<AddressRest>();
		List<AddressDto> lstAddressDto = addressService.getAddresses(userId);

		if (null != lstAddressDto && !lstAddressDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressRest>>() {
			}.getType();
			returnvalue = new ModelMapper().map(lstAddressDto, listType);
		}

		return new ResponseEntity<List<AddressRest>>(returnvalue, HttpStatus.OK);
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization", value="Book JWT Token",paramType = "header")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('LENDER')")
	@GetMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AddressRest> getAdddress(@PathVariable String userId, @PathVariable String addressId) {
		AddressRest returnvalue = new AddressRest();
		AddressDto addressDto = addressService.getAddress(addressId);

		ModelMapper modelMapper = new ModelMapper();
		returnvalue = modelMapper.map(addressDto, AddressRest.class);

		return new ResponseEntity<AddressRest>(returnvalue, HttpStatus.OK);
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name="authorization", value="Book JWT Token",paramType = "header")
	})
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(path = "/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AddressRest> updateAddress(@Valid @RequestBody AddressRequestModel addressDetails,@PathVariable String addressId) {
		AddressDto addressDto = new AddressDto();
		ModelMapper modelMapper = new ModelMapper();
		
		addressDto = addressService.getAddress(addressId);
		BeanUtils.copyProperties(addressDetails, addressDto);
		
		AddressDto updatedAddress = addressService.updateAddress(addressDto,addressId);

		AddressRest addressRest = new AddressRest();
		addressRest = modelMapper.map(updatedAddress, AddressRest.class);

		return new ResponseEntity<AddressRest>(addressRest,HttpStatus.OK);
	}

	
	
	
}
