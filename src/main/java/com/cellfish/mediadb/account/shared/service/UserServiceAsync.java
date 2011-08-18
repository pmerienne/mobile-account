package com.cellfish.mediadb.account.shared.service;

import java.util.List;

import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void authenticate(String email, String password, AsyncCallback<UserDTO> callback);

	void findAll(AsyncCallback<List<UserDTO>> callback);

	void findByName(String name, AsyncCallback<UserDTO> callback);

	void getCurrentUser(AsyncCallback<UserDTO> callback);

	void logout(AsyncCallback<Void> callback);

	void save(UserDTO user, AsyncCallback<UserDTO> callback);

}
