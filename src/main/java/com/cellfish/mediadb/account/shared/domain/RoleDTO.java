package com.cellfish.mediadb.account.shared.domain;

public enum RoleDTO {

	USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

	private String name;

	RoleDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
