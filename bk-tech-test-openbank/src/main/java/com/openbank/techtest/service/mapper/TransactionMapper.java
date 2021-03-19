package com.openbank.techtest.service.mapper;


import com.openbank.techtest.domain.*;
import com.openbank.techtest.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionTypeMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "transactionTypeId", target = "transactionType")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
