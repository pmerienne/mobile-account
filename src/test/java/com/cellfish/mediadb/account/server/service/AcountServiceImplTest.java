package com.cellfish.mediadb.account.server.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.cellfish.mediadb.account.server.domain.Bill;
import com.cellfish.mediadb.account.server.domain.Payment;
import com.cellfish.mediadb.account.server.domain.User;
import com.cellfish.mediadb.account.server.repositories.BillRepository;
import com.cellfish.mediadb.account.server.repositories.PaymentRepository;
import com.cellfish.mediadb.account.shared.domain.UserDTO;

@Ignore
public class AcountServiceImplTest {

	private AccountServiceImpl accountServiceImpl;

	private BillRepository billRepository;

	private PaymentRepository paymentRepository;

	private Mapper mapper = new DozerBeanMapper();

	@Before
	public void setUp() {
		this.accountServiceImpl = new AccountServiceImpl();
		this.billRepository = createMock(BillRepository.class);
		this.accountServiceImpl.setBillRepository(billRepository);
		this.paymentRepository = createMock(PaymentRepository.class);
		this.accountServiceImpl.setPaymentRepository(paymentRepository);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getAcountBalanceWithBill() {
		// Mocked data
		User smith = new User("smith", "john");
		User dupont = new User("dupont", "jean");

		Bill pub = new Bill(smith, 200);
		List<User> pubParticipants = Arrays.asList(smith, dupont);
		pub.setParticipants(pubParticipants);

		// Mock
		expect(billRepository.findByParticipants(smith)).andReturn(Arrays.asList(pub));
		expect(paymentRepository.findByToUserOrFromUser(smith)).andReturn(Collections.EMPTY_LIST);
		replay(billRepository, paymentRepository);
		// Call
		Map<UserDTO, Integer> smithAccountBalance = this.accountServiceImpl.getAcountBalance(this.mapper.map(smith, UserDTO.class));
		// Assert
		assertNotNull(smithAccountBalance);
		assertEquals(Integer.valueOf(-100), smithAccountBalance.get(this.mapper.map(dupont, UserDTO.class)));
	}

	@Test
	public void getAcountBalanceWithBillAndPayment() {
		// Mocked data
		User smith = new User("smith", "john");
		User dupont = new User("dupont", "jean");

		Bill pub = new Bill(smith, 200);
		List<User> pubParticipants = Arrays.asList(smith, dupont);
		pub.setParticipants(pubParticipants);

		Payment dupontToSmith = new Payment(dupont, smith, 90);

		// Mock
		expect(billRepository.findByParticipants(smith)).andReturn(Arrays.asList(pub));
		expect(paymentRepository.findByToUserOrFromUser(smith)).andReturn(Arrays.asList(dupontToSmith));
		replay(billRepository, paymentRepository);
		// Call
		Map<UserDTO, Integer> smithAccountBalance = this.accountServiceImpl.getAcountBalance(this.mapper.map(smith, UserDTO.class));
		// Assert
		assertNotNull(smithAccountBalance);
		assertEquals(Integer.valueOf(-10), smithAccountBalance.get(this.mapper.map(dupont, UserDTO.class)));
	}

	@Test
	public void getAcountBalanceWithNoMoreBalance() {
		// Mocked data
		User smith = new User("smith", "john");
		User dupont = new User("dupont", "jean");

		Bill pub = new Bill(smith, 200);
		List<User> pubParticipants = Arrays.asList(smith, dupont);
		pub.setParticipants(pubParticipants);

		Payment dupontToSmith = new Payment(dupont, smith, 100);

		// Mock
		expect(billRepository.findByParticipants(smith)).andReturn(Arrays.asList(pub));
		expect(paymentRepository.findByToUserOrFromUser(smith)).andReturn(Arrays.asList(dupontToSmith));
		replay(billRepository, paymentRepository);
		// Call
		Map<UserDTO, Integer> smithAccountBalance = this.accountServiceImpl.getAcountBalance(this.mapper.map(smith, UserDTO.class));
		// Assert
		assertNotNull(smithAccountBalance);
		assertNull(smithAccountBalance.get(this.mapper.map(dupont, UserDTO.class)));
	}

	@Test
	public void getAcountBalanceWithLotOfTransaction() {
		// Mocked data
		User smith = new User("smith", "john");
		User dupont = new User("dupont", "jean");
		User martin = new User("martin", "paul");

		Bill pub = new Bill(smith, 200);
		List<User> pubParticipants = Arrays.asList(martin, smith, dupont);
		pub.setParticipants(pubParticipants);
		Bill shoping = new Bill(smith, 80);
		List<User> shopingParticipants = Arrays.asList(dupont, smith);
		shoping.setParticipants(shopingParticipants);
		Bill rent = new Bill(martin, 300);
		List<User> rentParticipants = Arrays.asList(smith, dupont, martin);
		rent.setParticipants(rentParticipants);
		List<Bill> expectedBills = Arrays.asList(pub, shoping, rent);

		Payment smithToDupont = new Payment(smith, dupont, 30);
		Payment smithToMartin = new Payment(smith, martin, 250);
		Payment martinToSmith = new Payment(martin, smith, 20);
		List<Payment> expectedPayments = Arrays.asList(smithToDupont, smithToMartin, martinToSmith);
		// Mock
		expect(billRepository.findByParticipants(smith)).andReturn(expectedBills);
		expect(paymentRepository.findByToUserOrFromUser(smith)).andReturn(expectedPayments);
		replay(billRepository, paymentRepository);
		// Call
		Map<UserDTO, Integer> smithAccountBalance = this.accountServiceImpl.getAcountBalance(this.mapper.map(smith, UserDTO.class));
		// Assert
		assertNotNull(smithAccountBalance);
	}
}
