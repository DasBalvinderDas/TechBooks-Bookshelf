package rest.app.assignment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rest.app.assignment.persistence.entity.AddressEntity;
import rest.app.assignment.persistence.entity.UserEntity;
import rest.app.assignment.persistence.repositories.AddressRepository;
import rest.app.assignment.persistence.repositories.UserRepository;
import rest.app.assignment.service.AddressService;
import rest.app.assignment.shared.dto.AddressDto;
import rest.app.assignment.shared.dto.UserDto;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;

	
	@Override
	public List<AddressDto> getAddresses(String userId) {
		
		List<AddressDto> returnValue = new ArrayList<AddressDto>();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity==null) return returnValue;
		
		Iterable<AddressEntity> adddresses = addressRepository.findAllByUserDetails(userEntity);
		for (AddressEntity addressEntity : adddresses) {
			returnValue.add(new ModelMapper().map(addressEntity, AddressDto.class));
		}
		return returnValue;
	}
	
	@Override
	public AddressDto getAddress(String addressId) {
        AddressDto returnValue = new AddressDto();

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        
        if(addressEntity!=null)
        {
            returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
        }
 
        return returnValue;
	}

	@Override
	public AddressDto updateAddress(AddressDto addressDto,String addressId) {
		
		AddressDto returnValue = new AddressDto();
		ModelMapper modelMapper = new ModelMapper();
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		if (null == addressEntity)
			throw new RuntimeException("Adddress id provided does not exist");
		
		addressEntity.setCountry(addressDto.getCountry());
		addressEntity.setTown(addressDto.getTown());
		addressEntity.setPostalCode(addressDto.getPostalCode());
		addressEntity.setRegion(addressDto.getRegion());
		addressEntity.setLine1(addressDto.getLine1());
		addressEntity.setLine2(addressDto.getLine2());
		addressEntity.setPrimary(addressDto.isPrimary());
		
		addressEntity = addressRepository.save(addressEntity);
		returnValue = modelMapper.map(addressEntity, AddressDto.class);
		
		return returnValue;
	}

}
