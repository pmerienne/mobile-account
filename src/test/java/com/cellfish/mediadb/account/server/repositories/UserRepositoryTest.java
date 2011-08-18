package com.cellfish.mediadb.account.server.repositories;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import com.cellfish.mediadb.account.server.domain.Role;
import com.cellfish.mediadb.account.server.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	private User john;

	@Before
	public void setUp() {
		String email = "john.doo@cloud.com";
		String password = "md5Password";
		john = new User("cloud", "john");
		john.setEmail(email);
		john.setPassword(password);
		john.setRoles(Arrays.asList(Role.ADMIN, Role.USER));
		this.userRepository.save(john);
	}

	@Test
	public void findByEmail() {
		User actualUser = this.userRepository.findByEmail(john.getEmail());
		assertReflectionEquals(john, actualUser, ReflectionComparatorMode.IGNORE_DEFAULTS);
	}

}
