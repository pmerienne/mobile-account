package com.cellfish.mediadb.account.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cellfish.mediadb.account.server.domain.Payment;
import com.cellfish.mediadb.account.server.domain.User;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByFromUser(User user);

	Page<Payment> findByFromUser(User user, Pageable pageable);

	List<Payment> findByToUser(User user);

	Page<Payment> findByToUser(User user, Pageable pageable);

	List<Payment> findByToUserOrFromUser(User user);

	Page<Payment> findByToUserOrFromUser(User user, Pageable pageable);
}
