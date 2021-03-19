package com.openbank.techtest.web.rest;

import com.openbank.techtest.BktechtestopenbankApp;
import com.openbank.techtest.domain.Transaction;
import com.openbank.techtest.domain.TransactionType;
import com.openbank.techtest.repository.TransactionRepository;
import com.openbank.techtest.service.TransactionService;
import com.openbank.techtest.service.dto.TransactionDTO;
import com.openbank.techtest.service.mapper.TransactionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = BktechtestopenbankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {

    private static final Integer DEFAULT_ACCOUNT_ID = 1;
    private static final Integer UPDATED_ACCOUNT_ID = 2;

    private static final Integer DEFAULT_COUNTER_PARTY_ACCOUNT = 1;
    private static final Integer UPDATED_COUNTER_PARTY_ACCOUNT = 2;

    private static final String DEFAULT_COUNTER_PARTY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTER_PARTY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTER_PARTY_LOGO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_COUNTER_PARTY_LOGO_PATH = "BBBBBBBBBB";

    private static final Integer DEFAULT_INSTRUCTED_AMOUNT = 1;
    private static final Integer UPDATED_INSTRUCTED_AMOUNT = 2;

    private static final String DEFAULT_INSTRUCTED_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTED_CURRENCY = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRANSACTION_AMOUNT = 1;
    private static final Integer UPDATED_TRANSACTION_AMOUNT = 2;

    private static final String DEFAULT_TRANSACTION_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .accountId(DEFAULT_ACCOUNT_ID)
            .counterPartyAccount(DEFAULT_COUNTER_PARTY_ACCOUNT)
            .counterPartyName(DEFAULT_COUNTER_PARTY_NAME)
            .counterPartyLogoPath(DEFAULT_COUNTER_PARTY_LOGO_PATH)
            .instructedAmount(DEFAULT_INSTRUCTED_AMOUNT)
            .instructedCurrency(DEFAULT_INSTRUCTED_CURRENCY)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .transactionCurrency(DEFAULT_TRANSACTION_CURRENCY)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        TransactionType transactionType;
        if (TestUtil.findAll(em, TransactionType.class).isEmpty()) {
            transactionType = TransactionTypeResourceIT.createEntity(em);
            em.persist(transactionType);
            em.flush();
        } else {
            transactionType = TestUtil.findAll(em, TransactionType.class).get(0);
        }
        transaction.setTransactionType(transactionType);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .accountId(UPDATED_ACCOUNT_ID)
            .counterPartyAccount(UPDATED_COUNTER_PARTY_ACCOUNT)
            .counterPartyName(UPDATED_COUNTER_PARTY_NAME)
            .counterPartyLogoPath(UPDATED_COUNTER_PARTY_LOGO_PATH)
            .instructedAmount(UPDATED_INSTRUCTED_AMOUNT)
            .instructedCurrency(UPDATED_INSTRUCTED_CURRENCY)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrency(UPDATED_TRANSACTION_CURRENCY)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        TransactionType transactionType;
        if (TestUtil.findAll(em, TransactionType.class).isEmpty()) {
            transactionType = TransactionTypeResourceIT.createUpdatedEntity(em);
            em.persist(transactionType);
            em.flush();
        } else {
            transactionType = TestUtil.findAll(em, TransactionType.class).get(0);
        }
        transaction.setTransactionType(transactionType);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testTransaction.getCounterPartyAccount()).isEqualTo(DEFAULT_COUNTER_PARTY_ACCOUNT);
        assertThat(testTransaction.getCounterPartyName()).isEqualTo(DEFAULT_COUNTER_PARTY_NAME);
        assertThat(testTransaction.getCounterPartyLogoPath()).isEqualTo(DEFAULT_COUNTER_PARTY_LOGO_PATH);
        assertThat(testTransaction.getInstructedAmount()).isEqualTo(DEFAULT_INSTRUCTED_AMOUNT);
        assertThat(testTransaction.getInstructedCurrency()).isEqualTo(DEFAULT_INSTRUCTED_CURRENCY);
        assertThat(testTransaction.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testTransaction.getTransactionCurrency()).isEqualTo(DEFAULT_TRANSACTION_CURRENCY);
        assertThat(testTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setAccountId(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterPartyAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setCounterPartyAccount(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterPartyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setCounterPartyName(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterPartyLogoPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setCounterPartyLogoPath(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstructedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setInstructedAmount(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstructedCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setInstructedCurrency(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setTransactionAmount(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setTransactionCurrency(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setDescription(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].counterPartyAccount").value(hasItem(DEFAULT_COUNTER_PARTY_ACCOUNT)))
            .andExpect(jsonPath("$.[*].counterPartyName").value(hasItem(DEFAULT_COUNTER_PARTY_NAME)))
            .andExpect(jsonPath("$.[*].counterPartyLogoPath").value(hasItem(DEFAULT_COUNTER_PARTY_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].instructedAmount").value(hasItem(DEFAULT_INSTRUCTED_AMOUNT)))
            .andExpect(jsonPath("$.[*].instructedCurrency").value(hasItem(DEFAULT_INSTRUCTED_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT)))
            .andExpect(jsonPath("$.[*].transactionCurrency").value(hasItem(DEFAULT_TRANSACTION_CURRENCY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.counterPartyAccount").value(DEFAULT_COUNTER_PARTY_ACCOUNT))
            .andExpect(jsonPath("$.counterPartyName").value(DEFAULT_COUNTER_PARTY_NAME))
            .andExpect(jsonPath("$.counterPartyLogoPath").value(DEFAULT_COUNTER_PARTY_LOGO_PATH))
            .andExpect(jsonPath("$.instructedAmount").value(DEFAULT_INSTRUCTED_AMOUNT))
            .andExpect(jsonPath("$.instructedCurrency").value(DEFAULT_INSTRUCTED_CURRENCY))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT))
            .andExpect(jsonPath("$.transactionCurrency").value(DEFAULT_TRANSACTION_CURRENCY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .accountId(UPDATED_ACCOUNT_ID)
            .counterPartyAccount(UPDATED_COUNTER_PARTY_ACCOUNT)
            .counterPartyName(UPDATED_COUNTER_PARTY_NAME)
            .counterPartyLogoPath(UPDATED_COUNTER_PARTY_LOGO_PATH)
            .instructedAmount(UPDATED_INSTRUCTED_AMOUNT)
            .instructedCurrency(UPDATED_INSTRUCTED_CURRENCY)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .transactionCurrency(UPDATED_TRANSACTION_CURRENCY)
            .description(UPDATED_DESCRIPTION);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testTransaction.getCounterPartyAccount()).isEqualTo(UPDATED_COUNTER_PARTY_ACCOUNT);
        assertThat(testTransaction.getCounterPartyName()).isEqualTo(UPDATED_COUNTER_PARTY_NAME);
        assertThat(testTransaction.getCounterPartyLogoPath()).isEqualTo(UPDATED_COUNTER_PARTY_LOGO_PATH);
        assertThat(testTransaction.getInstructedAmount()).isEqualTo(UPDATED_INSTRUCTED_AMOUNT);
        assertThat(testTransaction.getInstructedCurrency()).isEqualTo(UPDATED_INSTRUCTED_CURRENCY);
        assertThat(testTransaction.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testTransaction.getTransactionCurrency()).isEqualTo(UPDATED_TRANSACTION_CURRENCY);
        assertThat(testTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
