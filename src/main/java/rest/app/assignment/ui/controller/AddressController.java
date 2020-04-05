package rest.app.assignment.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import rest.app.assignment.service.AddressService;
import rest.app.assignment.shared.dto.AddressDto;
import rest.app.assignment.ui.model.response.AddressRest;

public class AddressController {
	
	@Autowired
	AddressService addressService;

	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<AddressRest> getAdddresses(@PathVariable String id) {
		List<AddressRest> returnvalue = new ArrayList<AddressRest>();
		List<AddressDto> lstAddressDto = addressService.getAddresses(id);

		if(null!=lstAddressDto && !lstAddressDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressRest>>() {}.getType();
			returnvalue = new ModelMapper().map(lstAddressDto, listType);
		}
		
		return returnvalue;
	}
	
	@GetMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public AddressRest getAdddresses(@PathVariable String userId,@PathVariable String addressId) {
		AddressRest returnvalue = new AddressRest();
		AddressDto addressDto = addressService.getAddress(addressId);

		ModelMapper modelMapper = new ModelMapper();
		returnvalue = modelMapper.map(addressDto, AddressRest.class);
		
		return returnvalue;
	}
	
	@PutMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public AddressRest updateAdddress(@PathVariable String userId,@PathVariable String addressId) {
		AddressRest returnvalue = new AddressRest();
		AddressDto addressDto = addressService.getAddress(addressId);

		ModelMapper modelMapper = new ModelMapper();
		returnvalue = modelMapper.map(addressDto, AddressRest.class);
		
		return returnvalue;
	}
	
}