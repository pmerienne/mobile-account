package com.cellfish.mediadb.account.client.model;

import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;

public class BillSearch extends Search<BillDTO> {

	private UserDTO user;

	public BillSearch(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
