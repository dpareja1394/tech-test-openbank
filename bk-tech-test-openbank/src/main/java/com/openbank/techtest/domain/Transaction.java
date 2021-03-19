package com.openbank.techtest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Transaction\n@author Daniel Pareja\n@date 18 Mar 2021
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @NotNull
    @Column(name = "counter_party_account", nullable = false)
    private Integer counterPartyAccount;

    @NotNull
    @Column(name = "counter_party_name", nullable = false)
    private String counterPartyName;

    @NotNull
    @Column(name = "counter_party_logo_path", nullable = false)
    private String counterPartyLogoPath;

    @NotNull
    @Column(name = "instructed_amount", nullable = false)
    private Integer instructedAmount;

    @NotNull
    @Column(name = "instructed_currency", nullable = false)
    private String instructedCurrency;

    @NotNull
    @Column(name = "transaction_amount", nullable = false)
    private Integer transactionAmount;

    @NotNull
    @Column(name = "transaction_currency", nullable = false)
    private String transactionCurrency;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private TransactionType transactionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Transaction accountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCounterPartyAccount() {
        return counterPartyAccount;
    }

    public Transaction counterPartyAccount(Integer counterPartyAccount) {
        this.counterPartyAccount = counterPartyAccount;
        return this;
    }

    public void setCounterPartyAccount(Integer counterPartyAccount) {
        this.counterPartyAccount = counterPartyAccount;
    }

    public String getCounterPartyName() {
        return counterPartyName;
    }

    public Transaction counterPartyName(String counterPartyName) {
        this.counterPartyName = counterPartyName;
        return this;
    }

    public void setCounterPartyName(String counterPartyName) {
        this.counterPartyName = counterPartyName;
    }

    public String getCounterPartyLogoPath() {
        return counterPartyLogoPath;
    }

    public Transaction counterPartyLogoPath(String counterPartyLogoPath) {
        this.counterPartyLogoPath = counterPartyLogoPath;
        return this;
    }

    public void setCounterPartyLogoPath(String counterPartyLogoPath) {
        this.counterPartyLogoPath = counterPartyLogoPath;
    }

    public Integer getInstructedAmount() {
        return instructedAmount;
    }

    public Transaction instructedAmount(Integer instructedAmount) {
        this.instructedAmount = instructedAmount;
        return this;
    }

    public void setInstructedAmount(Integer instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getInstructedCurrency() {
        return instructedCurrency;
    }

    public Transaction instructedCurrency(String instructedCurrency) {
        this.instructedCurrency = instructedCurrency;
        return this;
    }

    public void setInstructedCurrency(String instructedCurrency) {
        this.instructedCurrency = instructedCurrency;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public Transaction transactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public Transaction transactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
        return this;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getDescription() {
        return description;
    }

    public Transaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Transaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", counterPartyAccount=" + getCounterPartyAccount() +
            ", counterPartyName='" + getCounterPartyName() + "'" +
            ", counterPartyLogoPath='" + getCounterPartyLogoPath() + "'" +
            ", instructedAmount=" + getInstructedAmount() +
            ", instructedCurrency='" + getInstructedCurrency() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionCurrency='" + getTransactionCurrency() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
