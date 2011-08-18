package com.cellfish.mediadb.account.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDTO implements Serializable {

	private static final long serialVersionUID = -7457239551182434783L;
	private Long id;

	private UserDTO paymaster;

	private List<UserDTO> participants;

	private Date creationDate;

	private String shortDescription;

	private String longDescription;

	private Integer amount;

	public BillDTO() {
		this.creationDate = new Date();
		this.participants = new ArrayList<UserDTO>();
	}

	public BillDTO(UserDTO paymaster, Integer amount) {
		super();
		this.paymaster = paymaster;
		this.amount = amount;
		this.creationDate = new Date();
		this.participants = new ArrayList<UserDTO>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public UserDTO getPaymaster() {
		return paymaster;
	}

	public void setPaymaster(UserDTO paymaster) {
		this.paymaster = paymaster;
	}

	public List<UserDTO> getParticipants() {
		return participants;
	}

	public void setParticipants(List<UserDTO> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", paymaster=" + paymaster + ", participants=" + participants + ", creationDate=" + creationDate
				+ ", shortDescription=" + shortDescription + ", longDescription=" + longDescription + ", amount=" + amount + "]";
	}

}
