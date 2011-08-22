package com.cellfish.mediadb.account.client.model.search;

import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;

public class BillSearch extends Search<BillDTO> {

	private UserDTO user;

	public BillSearch(UserDTO user, Integer page, Integer window) {
		this.user = user;
		this.setPage(page);
		this.setWindow(window);
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
