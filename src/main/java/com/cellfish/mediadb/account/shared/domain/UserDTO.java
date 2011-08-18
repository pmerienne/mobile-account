package com.cellfish.mediadb.account.shared.domain;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = -8104054030525843108L;

	private Long id;

	private String name;

	private String firstName;

	private String email;

	private String password;

	private List<RoleDTO> roles;

	public UserDTO() {
	}

	public UserDTO(String name, String firstName) {
		super();
		this.name = name;
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstName=" + firstName + ", email=" + email + ", password=" + password
				+ ", roles=" + roles + "]";
	}

}
