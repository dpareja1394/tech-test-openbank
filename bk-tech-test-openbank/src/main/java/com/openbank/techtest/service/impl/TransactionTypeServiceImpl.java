package com.openbank.techtest.service.impl;

import com.openbank.techtest.service.TransactionTypeService;
import com.openbank.techtest.domain.TransactionType;
import com.openbank.techtest.repository.TransactionTypeRepository;
import com.openbank.techtest.service.dto.TransactionTypeDTO;
import com.openbank.techtest.service.mapper.TransactionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionType}.
 */
@Service
@Transactional
public class TransactionTypeServiceImpl implements TransactionTypeService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeServiceImpl.class);

    private final TransactionTypeRepository transactionTypeRepository;

    private final TransactionTypeMapper transactionTypeMapper;

    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository, TransactionTypeMapper transactionTypeMapper) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionTypeMapper = transactionTypeMapper;
    }

    @Override
    public TransactionTypeDTO save(TransactionTypeDTO transactionTypeDTO) {
        log.debug("Request to save TransactionType : {}", transactionTypeDTO);
        TransactionType transactionType = transactionTypeMapper.toEntity(transactionTypeDTO);
        transactionType = transactionTypeRepository.save(transactionType);
        return transactionTypeMapper.toDto(transactionType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionTypes");
        return transactionTypeRepository.findAll(pageable)
            .map(transactionTypeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionTypeDTO> findOne(Long id) {
        log.debug("Request to get TransactionType : {}", id);
        return transactionTypeRepository.findById(id)
            .map(transactionTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionType : {}", id);
        transactionTypeRepository.deleteById(id);
    }
}
