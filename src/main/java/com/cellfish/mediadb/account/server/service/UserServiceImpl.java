package com.cellfish.mediadb.account.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cellfish.mediadb.account.server.domain.Role;
import com.cellfish.mediadb.account.server.domain.User;
import com.cellfish.mediadb.account.server.repositories.UserRepository;
import com.cellfish.mediadb.account.shared.domain.UserDTO;
import com.cellfish.mediadb.account.shared.service.UserService;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, InitializingBean {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mapper mapper;

	public UserDTO findByName(String name) {
		User user = userRepository.findByName(name);
		return this.mapper.map(user, UserDTO.class);
	}

	public List<UserDTO> findAll() {
		List<User> users = userRepository.findAll();
		List<UserDTO> results = new ArrayList<UserDTO>();
		for (User user : users) {
			results.add(this.mapper.map(user, UserDTO.class));
		}
		return results;
	}

	public UserDTO save(UserDTO user) {
		User savedUser = this.userRepository.save(this.mapper.map(user, User.class));
		return this.mapper.map(savedUser, UserDTO.class);
	}

	public UserDTO authenticate(String email, String password) {
		User accountUser = checkCredentials(email, password);
		if (accountUser != null) {
			UserDetails user = buildUserDetails(accountUser);
			Authentication auth = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
			SecurityContext sc = new SecurityContextImpl();
			sc.setAuthentication(auth);
			SecurityContextHolder.setContext(sc);
			return this.mapper.map(accountUser, UserDTO.class);
		} else {
			return null;
		}
	}

	public void logout() {
		SecurityContextHolder.clearContext();
	}

	public UserDTO getCurrentUser() {
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc != null) {
			Authentication authentication = sc.getAuthentication();
			if (authentication != null) {
				Object principal = authentication.getPrincipal();
				if (principal != null && principal instanceof User) {
					return this.mapper.map(principal, UserDTO.class);
				}
			}
		}
		return null;
	}

	private User checkCredentials(String email, String password) {
		User user = this.userRepository.findByEmail(email);
		if (user == null) {
			return null;
		}
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	private UserDetails buildUserDetails(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true,
				true, true, authorities);
		return userDetails;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	// TODO remove
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("AIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIEAIE");
		User admin = new User("Doo", "John");
		admin.setEmail("admin@gmail.com");
		admin.setPassword("admin");
		admin.setRoles(Arrays.asList(Role.ADMIN, Role.USER));
		this.userRepository.save(admin);
	}
}
