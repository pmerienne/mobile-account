package com.cellfish.mediadb.account.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cellfish.mediadb.account.server.domain.Bill;
import com.cellfish.mediadb.account.server.domain.Payment;
import com.cellfish.mediadb.account.server.domain.User;
import com.cellfish.mediadb.account.server.repositories.BillRepository;
import com.cellfish.mediadb.account.server.repositories.PaymentRepository;
import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.PaymentDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.cellfish.mediadb.account.shared.service.AccountService;

@Service("accountService")
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private Mapper mapper;

	public void save(BillDTO bill) {
		this.billRepository.save(this.mapper.map(bill, Bill.class));
	}

	public List<BillDTO> findBillsByUser(UserDTO userDTO, int page, int pageSize) {
		User user = this.mapper.map(userDTO, User.class);
		List<Bill> bills = this.billRepository.findByParticipants(user, new PageRequest(page, pageSize)).getContent();
		return this.map(bills, BillDTO.class);
	}

	public List<PaymentDTO> findPaymentByUser(UserDTO userDTO, int page, int pageSize) {
		User user = this.mapper.map(userDTO, User.class);
		List<Payment> payments = this.paymentRepository.findByToUserOrFromUser(user, new PageRequest(page, pageSize)).getContent();
		return this.map(payments, PaymentDTO.class);
	}

	public List<PaymentDTO> findPaymentFromUser(UserDTO userDTO, int page, int pageSize) {
		User user = this.mapper.map(userDTO, User.class);
		List<Payment> payments = this.paymentRepository.findByFromUser(user, new PageRequest(page, pageSize)).getContent();
		return this.map(payments, PaymentDTO.class);
	}

	public List<PaymentDTO> findPaymentToUser(UserDTO userDTO, int page, int pageSize) {
		User user = this.mapper.map(userDTO, User.class);
		List<Payment> payments = this.paymentRepository.findByToUser(user, new PageRequest(page, pageSize)).getContent();
		return this.map(payments, PaymentDTO.class);
	}

	public void save(PaymentDTO paymentDTO) {
		Payment payment = this.mapper.map(paymentDTO, Payment.class);
		this.paymentRepository.save(payment);
	}

	public Boolean isValidated(PaymentDTO payment) {
		boolean fromValidate = payment.getPaymentValidation().contains(payment.getFromUser());
		boolean toValidate = payment.getPaymentValidation().contains(payment.getToUser());
		return fromValidate && toValidate;
	}

	public void validate(PaymentDTO paymentDTO, UserDTO user) {
		Payment payment = this.paymentRepository.findOne(paymentDTO.getId());
		payment.getPaymentValidation().add(this.mapper.map(user, User.class));
	}

	public Map<UserDTO, Integer> getAcountBalance(UserDTO userDTO) {
		User user = this.mapper.map(userDTO, User.class);
		Map<User, Integer> acountBalance = new HashMap<User, Integer>();
		List<Bill> userBills = this.billRepository.findByParticipants(user);
		List<Payment> userPayments = this.paymentRepository.findByToUserOrFromUser(user);
		for (Bill bill : userBills) {
			addBillToAcountBalance(acountBalance, bill, user);
		}
		for (Payment payment : userPayments) {
			if (payment.getFromUser().equals(user)) {
				addTransaction(acountBalance, payment.getToUser(), -payment.getAmount());
			} else {
				addTransaction(acountBalance, payment.getFromUser(), payment.getAmount());
			}
		}
		// Remove all balance to 0
		List<User> noBalanceUsers = new ArrayList<User>();
		for (User otherUser : acountBalance.keySet()) {
			if (acountBalance.get(otherUser) == 0) {
				noBalanceUsers.add(otherUser);
			}
		}
		for (User noBalanceUser : noBalanceUsers) {
			acountBalance.remove(noBalanceUser);
		}
		return this.map(acountBalance, UserDTO.class);
	}

	private void addBillToAcountBalance(Map<User, Integer> acountBalance, Bill bill, User user) {
		User paymaster = bill.getPaymaster();
		List<User> participants = bill.getParticipants();
		Integer billPart = bill.getAmount() / participants.size();
		if (paymaster.equals(user)) {
			for (User participant : participants) {
				if (!participant.equals(user)) {
					addTransaction(acountBalance, participant, -billPart);
				}
			}
		} else {
			addTransaction(acountBalance, paymaster, billPart);
		}
	}

	private void addTransaction(Map<User, Integer> acountBalance, User user, Integer value) {
		if (!acountBalance.containsKey(user)) {
			acountBalance.put(user, 0);
		}
		acountBalance.put(user, acountBalance.get(user) + value);
	}

	public void setBillRepository(BillRepository billRepository) {
		this.billRepository = billRepository;
	}

	public void setPaymentRepository(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	private <T> List<T> map(List<?> source, Class<T> clazz) throws MappingException {
		List<T> results = new ArrayList<T>();
		for (Object o : source) {
			results.add(this.mapper.map(o, clazz));
		}
		return results;
	}

	private <T, U> Map<T, U> map(Map<?, U> source, Class<T> clazz) throws MappingException {
		Map<T, U> results = new HashMap<T, U>();
		for (Object key : source.keySet()) {
			results.put(this.mapper.map(key, clazz), source.get(key));
		}
		return results;
	}
}
