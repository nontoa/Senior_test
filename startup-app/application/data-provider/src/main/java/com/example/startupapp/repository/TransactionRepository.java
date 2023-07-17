package com.example.startupapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.startupapp.dao.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

	@Query("SELECT t FROM Transaction t WHERE t.orderId = :orderId")
	List<Transaction> findByOrderId(Long orderId);

}
