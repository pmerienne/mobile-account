package com.cellfish.mediadb.account.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Payment implements Serializable {

	private static final long serialVersionUID = -1589182469677871730L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private User fromUser;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private User toUser;

	private Integer amount;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<User> paymentValidation;

	@Temporal(TemporalType.DATE)
	private Date date;

	public Payment() {
	}

	public Payment(User from, User to, Integer amount) {
		super();
		this.fromUser = from;
		this.toUser = to;
		this.amount = amount;
		this.date = new Date();
		this.paymentValidation = new HashSet<User>();
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User from) {
		this.fromUser = from;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User to) {
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

	public Set<User> getPaymentValidation() {
		return paymentValidation;
	}

	public void setPaymentValidation(Set<User> paymentValidation) {
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
