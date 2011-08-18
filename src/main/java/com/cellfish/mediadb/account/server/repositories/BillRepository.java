package com.cellfish.mediadb.account.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cellfish.mediadb.account.server.domain.Bill;
import com.cellfish.mediadb.account.server.domain.User;

public interface BillRepository extends JpaRepository<Bill, Long> {

	List<Bill> findByPaymaster(User paymaster);

	Page<Bill> findByPaymaster(User paymaster, Pageable pageable);

	Page<Bill> findByParticipants(User participant, Pageable pageable);

	List<Bill> findByParticipants(User participant);
}
