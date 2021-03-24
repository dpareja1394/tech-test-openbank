package com.openbank.techtest.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openbank.techtest.domain.Transaction;
import com.openbank.techtest.repository.TransactionRepository;
import com.openbank.techtest.service.TransactionService;
import com.openbank.techtest.service.dto.TransactionDTO;
import com.openbank.techtest.service.mapper.TransactionMapper;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        log.debug("Request to save Transaction : {}", transactionDTO);
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable)
            .map(transactionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id)
            .map(transactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }
    
    /**
	 * @author Daniel Pareja Londoño
	 * @version Mar 24, 2021
	 *
	 * @see com.openbank.techtest.service.TransactionService#findAll()
	 *
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TransactionDTO> findAll() {
		List<Transaction> allTransactions = transactionRepository.findAll();
		List<TransactionDTO> allTransactionsDtos = transactionMapper.toDto(allTransactions);
		return allTransactionsDtos;
	}

	/**
	 * Find the transactions filter for field TransactionTypeId
	 * 
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 *
	 * @see com.openbank.techtest.service.TransactionService#findByTransactionTypeId(java.lang.Long)
	 *
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TransactionDTO> findByTransactionTypeId(Long transactionTypeId) {
		
		// Find all transactions filter by TransactionType
		List<Transaction> transactionsForTransactionType = transactionRepository.findByTransactionTypeId(transactionTypeId);
		
		// Mapping to DTO all transactions filter by TransactionType
		List<TransactionDTO> transactionsDTO = transactionMapper.toDto(transactionsForTransactionType);
		
		// Return DTO List
		return transactionsDTO;
	}

	/**
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 *
	 * @see com.openbank.techtest.service.TransactionService#totalAmmountByTransactionType(java.util.List)
	 *
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer totalAmountByTransactionType(Long transactionTypeId) {
		
		List<TransactionDTO> transactions;
		if(transactionTypeId!=null && transactionTypeId.equals(0L)) {
			transactions = this.findAll();
		}else {
			transactions = this.findByTransactionTypeId(transactionTypeId);
		}
		Integer totalAmount = transactions.stream().collect(Collectors.summingInt(t->t.getTransactionAmount()));
		return totalAmount ;
	}
	
}
