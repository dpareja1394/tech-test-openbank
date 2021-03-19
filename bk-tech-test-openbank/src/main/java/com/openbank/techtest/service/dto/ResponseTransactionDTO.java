package com.openbank.techtest.service.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * A DTO Generic Response for Transaction
 */
@ApiModel(description = "ResponseTransaction\n@author Daniel Pareja\n@date 18 Mar 2021")
public class ResponseTransactionDTO implements Serializable {
    
    /**
     * Fields from DTO
     *
     * @author Daniel Pareja Londoño
     * @version Mar 19, 2021
     * @since 1.8
     *
     */
    private List<TransactionDTO> transactionsFilterByTransactionType;
    private Integer totalAmmountForTransactionType;
	/**
	 *
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 * @since 1.8
	 * @return The transactionsFilterByTransactionType
	 *
	 */
	public List<TransactionDTO> getTransactionsFilterByTransactionType() {
		return transactionsFilterByTransactionType;
	}
	/**
	 *
	 * @param transactionsFilterByTransactionType The transactionsFilterByTransactionType to set
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 * @since 1.8
	 *
	 */
	public void setTransactionsFilterByTransactionType(List<TransactionDTO> transactionsFilterByTransactionType) {
		this.transactionsFilterByTransactionType = transactionsFilterByTransactionType;
	}
	/**
	 *
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 * @since 1.8
	 * @return The totalAmmountForTransactionType
	 *
	 */
	public Integer getTotalAmmountForTransactionType() {
		return totalAmmountForTransactionType;
	}
	/**
	 *
	 * @param totalAmmountForTransactionType The totalAmmountForTransactionType to set
	 * @author Daniel Pareja Londoño
	 * @version Mar 19, 2021
	 * @since 1.8
	 *
	 */
	public void setTotalAmmountForTransactionType(Integer totalAmmountForTransactionType) {
		this.totalAmmountForTransactionType = totalAmmountForTransactionType;
	}
    
    
}
