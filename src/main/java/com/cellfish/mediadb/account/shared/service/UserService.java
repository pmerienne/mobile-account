package com.cellfish.mediadb.account.shared.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userService.rpc")
public interface UserService extends RemoteService {

	@Secured("ROLE_USER")
	UserDTO findByName(String name);

	@Secured("ROLE_USER")
	List<UserDTO> findAll();

	UserDTO save(UserDTO user);

	UserDTO authenticate(String email, String password);

	void logout();

	UserDTO getCurrentUser();
}
