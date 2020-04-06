package rest.app.assignment.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import rest.app.assignment.persistence.entity.RoleEntity;
import rest.app.assignment.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	public UserDto createUser(UserDto userDto);
	public UserDto getUser(String email) ;
	public UserDto getUserByUserId(String id);
	public UserDto updateUser(String id, UserDto userDto) ;
	public void deleteUser(String id) ;
	public List<UserDto> getAllUsers();
	public List<UserDto> getUsers(int page, int limit);
	UserDto updateUserRole(String userId, UserDto userDto);
	public List<RoleEntity> getRoleEntityList(Iterator<String> itr);
	public long getIdForRoleName(String roleName);
	public String getUserIdForRoleId(String roleId);
	public List<UserDto> findAllUsersById(long id);
	
}
