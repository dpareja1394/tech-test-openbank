package com.openbank.techtest.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.openbank.techtest.domain.Transaction} entity.
 */
@ApiModel(description = "Transaction\n@author Daniel Pareja\n@date 18 Mar 2021")
public class TransactionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer accountId;

    @NotNull
    private Integer counterPartyAccount;

    @NotNull
    private String counterPartyName;

    @NotNull
    private String counterPartyLogoPath;

    @NotNull
    private Integer instructedAmount;

    @NotNull
    private String instructedCurrency;

    @NotNull
    private Integer transactionAmount;

    @NotNull
    private String transactionCurrency;

    @NotNull
    private String description;


    private Long transactionTypeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCounterPartyAccount() {
        return counterPartyAccount;
    }

    public void setCounterPartyAccount(Integer counterPartyAccount) {
        this.counterPartyAccount = counterPartyAccount;
    }

    public String getCounterPartyName() {
        return counterPartyName;
    }

    public void setCounterPartyName(String counterPartyName) {
        this.counterPartyName = counterPartyName;
    }

    public String getCounterPartyLogoPath() {
        return counterPartyLogoPath;
    }

    public void setCounterPartyLogoPath(String counterPartyLogoPath) {
        this.counterPartyLogoPath = counterPartyLogoPath;
    }

    public Integer getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(Integer instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getInstructedCurrency() {
        return instructedCurrency;
    }

    public void setInstructedCurrency(String instructedCurrency) {
        this.instructedCurrency = instructedCurrency;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
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
            ", transactionTypeId=" + getTransactionTypeId() +
            "}";
    }
}
