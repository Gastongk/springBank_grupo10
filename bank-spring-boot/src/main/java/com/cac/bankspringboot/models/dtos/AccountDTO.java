package com.cac.bankspringboot.models.dtos;

import com.cac.bankspringboot.models.dtos.enums.AccountType;
import jakarta.persistence.AccessType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;

    private AccountType type;

    private String cbu;

    private String alias;

    private BigDecimal amount;

    //private User owner;
}
