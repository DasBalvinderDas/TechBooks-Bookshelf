package rest.app.assignment.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import rest.app.assignment.exceptions.UserServiceException;
import rest.app.assignment.persistence.entity.RoleEntity;
import rest.app.assignment.persistence.entity.UserEntity;
import rest.app.assignment.persistence.repositories.RoleRepository;
import rest.app.assignment.persistence.repositories.UserRepository;
import rest.app.assignment.security.UserPrincipal;
import rest.app.assignment.service.UserService;
import rest.app.assignment.shared.Utils;
import rest.app.assignment.shared.dto.AddressDto;
import rest.app.assignment.shared.dto.UserDto;
import rest.app.assignment.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {

		UserDto returnValue = new UserDto();
		
			UserEntity storedUserDetails = userRepository.findByEmail(userDto.getEmail());

			if (storedUserDetails != null)
				throw new RuntimeException("Record Already exists");

			for (int i = 0; i < userDto.getAddresses().size(); i++) {
				AddressDto address = userDto.getAddresses().get(i);

				address.setAddressId(utils.generateAddressId(20));
				address.setUserDetails(userDto);

				userDto.getAddresses().set(i, address);
			}

			ModelMapper modelMapper = new ModelMapper();
			UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

			String publicUserId = utils.generateUserId(5);

			userEntity.setUserId(publicUserId);

			userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			UserEntity storedUserDetaills;

			// Set roles
			Collection<RoleEntity> roleEntities = new HashSet<>();
			for (String role : userDto.getRoles()) {
				RoleEntity roleEntity = roleRepository.findByName(role);
				if (roleEntity != null) {
					roleEntities.add(roleEntity);
				}
			}

			userEntity.setRoles(roleEntities);
			try {
				storedUserDetaills = userRepository.save(userEntity);
				returnValue = modelMapper.map(storedUserDetaills, UserDto.class);
			} catch (Exception ex) {
				throw new UserServiceException(ErrorMessages.COULD_NOT_CREATE_RECORD.getErrorMessage());
			}

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (null == userEntity)
			throw new UsernameNotFoundException(email);
		
		return new UserPrincipal(userEntity);
	}

	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if (null == userEntity)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (null == userEntity)
			throw new UsernameNotFoundException(String.valueOf(userId));

		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		
		UserDto returnValue = new UserDto();
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (null == userEntity)
			throw new UsernameNotFoundException(String.valueOf(userId));
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		
		userEntity = userRepository.save(userEntity);
		
		returnValue = modelMapper.map(userEntity, UserDto.class);
		
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (null == userEntity)
			throw new UsernameNotFoundException(String.valueOf(userId));

		userRepository.delete(userEntity);
		
	}

	@Override
	public UserDto getAllLenderRoleUsers() {
		ModelMapper modelMapper = new ModelMapper();
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findAllLenderRoleUsers();
		returnValue = modelMapper.map(userEntity, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDto getAllBorrowerRoleUsers() {
		ModelMapper modelMapper = new ModelMapper();
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findAllBorrowerRoleUsers();
		returnValue = modelMapper.map(userEntity, UserDto.class);
		return returnValue;
	}

	

	
}
