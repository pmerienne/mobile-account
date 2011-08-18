package com.cellfish.mediadb.account.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = -1589182469677871730L;

	private Long id;

	private UserDTO fromUser;

	private UserDTO toUser;

	private Integer amount;

	private Set<UserDTO> paymentValidation;

	private Date date;

	public PaymentDTO() {
	}

	public PaymentDTO(UserDTO from, UserDTO to, Integer amount) {
		super();
		this.fromUser = from;
		this.toUser = to;
		this.amount = amount;
		this.date = new Date();
		this.paymentValidation = new HashSet<UserDTO>();
	}

	public UserDTO getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserDTO from) {
		this.fromUser = from;
	}

	public UserDTO getToUser() {
		return toUser;
	}

	public void setToUser(UserDTO to) {
		this.toUser = to;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<UserDTO> getPaymentValidation() {
		return paymentValidation;
	}

	public void setPaymentValidation(Set<UserDTO> paymentValidation) {
		this.paymentValidation = paymentValidation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", from=" + fromUser + ", to=" + toUser + ", amount=" + amount + ", paymentValidation="
				+ paymentValidation + ", date=" + date + "]";
	}

}
