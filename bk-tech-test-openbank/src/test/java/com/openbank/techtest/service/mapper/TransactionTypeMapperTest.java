package com.openbank.techtest.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTypeMapperTest {

    private TransactionTypeMapper transactionTypeMapper;

    @BeforeEach
    public void setUp() {
        transactionTypeMapper = new TransactionTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionTypeMapper.fromId(null)).isNull();
    }
}
