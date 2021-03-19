package com.openbank.techtest.service;

import com.openbank.techtest.service.dto.TransactionTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.openbank.techtest.domain.TransactionType}.
 */
public interface TransactionTypeService {

    /**
     * Save a transactionType.
     *
     * @param transactionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionTypeDTO save(TransactionTypeDTO transactionTypeDTO);

    /**
     * Get all the transactionTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transactionType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionTypeDTO> findOne(Long id);

    /**
     * Delete the "id" transactionType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
