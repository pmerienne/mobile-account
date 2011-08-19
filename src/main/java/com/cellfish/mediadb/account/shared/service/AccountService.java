package com.cellfish.mediadb.account.shared.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;

import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.PaymentDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accountService.rpc")
public interface AccountService extends RemoteService {

	@Secured("ROLE_USER")
	void save(BillDTO bill);

	@Secured("ROLE_USER")
	List<BillDTO> findBillsByUser(UserDTO user, int page, int pageSize);

	@Secured("ROLE_USER")
	List<PaymentDTO> findPaymentByUser(UserDTO user, int page, int pageSize);

	@Secured("ROLE_USER")
	List<PaymentDTO> findPaymentFromUser(UserDTO user, int page, int pageSize);

	@Secured("ROLE_USER")
	List<PaymentDTO> findPaymentToUser(UserDTO user, int page, int pageSize);

	@Secured("ROLE_USER")
	void save(PaymentDTO payment);

	@Secured("ROLE_USER")
	Boolean isValidated(PaymentDTO payment);

	@Secured("ROLE_USER")
	void validate(PaymentDTO payment, UserDTO user);

	@Secured("ROLE_USER")
	Map<UserDTO, Integer> getAcountBalance(UserDTO user);
}
