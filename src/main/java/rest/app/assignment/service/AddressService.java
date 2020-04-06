package rest.app.assignment.service;

import java.util.List;

import rest.app.assignment.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String id);

	AddressDto getAddress(String addressId);

	AddressDto updateAddress(AddressDto addressDto,String addressId);

}
