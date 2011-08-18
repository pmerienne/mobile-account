package com.cellfish.mediadb.account.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bill implements Serializable {

	private static final long serialVersionUID = -7457239551182434783L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User paymaster;

	@ManyToMany
	private List<User> participants;

	@Temporal(TemporalType.DATE)
	private Date creationDate;

	private String shortDescription;

	private String longDescription;

	private Integer amount;

	public Bill() {
		this.creationDate = new Date();
		this.participants = new ArrayList<User>();
	}

	public Bill(User paymaster, Integer amount) {
		super();
		this.paymaster = paymaster;
		this.amount = amount;
		this.creationDate = new Date();
		this.participants = new ArrayList<User>();
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

	public User getPaymaster() {
		return paymaster;
	}

	public void setPaymaster(User paymaster) {
		this.paymaster = paymaster;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", paymaster=" + paymaster + ", participants=" + participants + ", creationDate=" + creationDate
				+ ", shortDescription=" + shortDescription + ", longDescription=" + longDescription + ", amount=" + amount + "]";
	}

}
