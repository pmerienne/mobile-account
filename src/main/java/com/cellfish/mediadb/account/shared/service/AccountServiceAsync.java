package com.cellfish.mediadb.account.shared.service;

import java.util.List;
import java.util.Map;

import com.cellfish.mediadb.account.shared.domain.BillDTO;
import com.cellfish.mediadb.account.shared.domain.PaymentDTO;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountServiceAsync {

	void validate(PaymentDTO payment, UserDTO user, AsyncCallback<Void> callback);

	void save(BillDTO bill, AsyncCallback<Void> callback);

	void save(PaymentDTO payment, AsyncCallback<Void> callback);

	void findPaymentByUser(UserDTO user, int page, int pageSize, AsyncCallback<List<PaymentDTO>> callback);

	void findBillsByUser(UserDTO user, int page, int pageSize, AsyncCallback<List<BillDTO>> callback);

	void findPaymentFromUser(UserDTO user, int page, int pageSize, AsyncCallback<List<PaymentDTO>> callback);

	void findPaymentToUser(UserDTO user, int page, int pageSize, AsyncCallback<List<PaymentDTO>> callback);

	void getAcountBalance(UserDTO user, AsyncCallback<Map<UserDTO, Integer>> callback);

	void isValidated(PaymentDTO payment, AsyncCallback<Boolean> callback);


}
