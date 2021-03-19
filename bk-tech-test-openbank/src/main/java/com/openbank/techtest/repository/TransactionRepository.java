package com.openbank.techtest.repository;

import com.openbank.techtest.domain.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	/**
	 * Find the transactions filter for field TransactionTypeId
	 *
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 * @since 1.8
	 * @param transactionTypeId
	 * @return
	 * @return <b>{@code List<Transaction>}</b> 
	 *
	 */
	List<Transaction> findByTransactionTypeId(Long transactionTypeId);
}
