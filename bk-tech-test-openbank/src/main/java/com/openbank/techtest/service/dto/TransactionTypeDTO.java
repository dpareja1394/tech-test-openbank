package com.openbank.techtest.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.openbank.techtest.domain.TransactionType} entity.
 */
@ApiModel(description = "TransactionType\n@author Daniel Pareja\n@date 18 Mar 2021")
public class TransactionTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nameTransactionType;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTransactionType() {
        return nameTransactionType;
    }

    public void setNameTransactionType(String nameTransactionType) {
        this.nameTransactionType = nameTransactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionTypeDTO{" +
            "id=" + getId() +
            ", nameTransactionType='" + getNameTransactionType() + "'" +
            "}";
    }
}
