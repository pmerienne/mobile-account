package com.cellfish.mediadb.account.server.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cellfish.mediadb.account.server.domain.Bill;
import com.cellfish.mediadb.account.server.domain.User;
import com.cellfish.mediadb.account.server.repositories.BillRepository;
import com.cellfish.mediadb.account.server.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class BillRepositoryTest {

	@Autowired
	BillRepository billRepository;

	@Autowired
	UserRepository userRepository;

	private User smith;
	private User dupont;
	private User martin;

	@Before
	public void setUp() {
		smith = new User("smith", "john");
		dupont = new User("dupont", "jean");
		martin = new User("martin", "paul");
		this.userRepository.save(smith);
		this.userRepository.save(dupont);
		this.userRepository.save(martin);

		Bill pub = new Bill(smith, 200);
		List<User> pubParticipants = Arrays.asList(martin, smith, dupont);
		pub.setParticipants(pubParticipants);
		this.billRepository.save(pub);

		Bill shoping = new Bill(smith, 800);
		List<User> shopingParticipants = Arrays.asList(dupont, smith);
		shoping.setParticipants(shopingParticipants);
		this.billRepository.save(shoping);

		Bill rent = new Bill(martin, 2010);
		List<User> rentParticipants = Arrays.asList(smith, dupont, martin);
		rent.setParticipants(rentParticipants);
		this.billRepository.save(rent);
	}

	@Test
	public void findByPayMaster() {
		List<Bill> actualBills = this.billRepository.findByPaymaster(smith);
		assertEquals(2, actualBills.size());
	}

	@Test
	public void findByParticipants() {
		List<Bill> actualBills = this.billRepository.findByParticipants(dupont);
		assertEquals(3, actualBills.size());
	}
}
