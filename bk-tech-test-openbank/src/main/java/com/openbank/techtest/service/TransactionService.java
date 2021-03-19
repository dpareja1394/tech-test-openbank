package com.openbank.techtest.service;

import com.openbank.techtest.service.dto.ResponseTransactionDTO;
import com.openbank.techtest.service.dto.TransactionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.openbank.techtest.domain.Transaction}.
 */
public interface TransactionService {

    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionDTO save(TransactionDTO transactionDTO);

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionDTO> findOne(Long id);

    /**
     * Delete the "id" transaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Find the transactions filter for field TransactionTypeId
     *
     * @author Daniel Pareja Londoño
     * @version Mar 19, 2021
     * @since 1.8
     * @param transactionTypeId
	 * @return <b>{@code List<TransactionDTO>}</b> 
     *
     */
    List<TransactionDTO> findByTransactionTypeId(Long transactionTypeId);
    
    /**
     * Calcule the Total from transactions list
     *
     * @author Daniel Pareja Londoño
     * @version Mar 19, 2021
     * @since 1.8
     * @param transactions
     * @return <b>{@code Integer total}</b> Start here...
     *
     */
    Integer totalAmountByTransactionType(List<TransactionDTO> transactions);
    
    /**
     * Return DTO Object with Transaction List and Total Transaction Amount
     *
     * @author Daniel Pareja Londoño
     * @version Mar 19, 2021
     * @since 1.8
     * @param transactionTypeId
     * @return
     * @return <b>{@code }</b> Start here...
     *
     */
    Optional<ResponseTransactionDTO> findTransactionsByTransactionType(Long transactionTypeId);
    
}
