package com.cac.bankspringboot.mappers;

import com.cac.bankspringboot.models.Account;
import com.cac.bankspringboot.models.dtos.AccountDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    //Refactorizar con Patron BUILDER
    public AccountDTO accountToDto(Account account){
        AccountDTO dto = new AccountDTO();
        dto.setAlias(account.getAlias());
        dto.setCbu(account.getCbu());
        dto.setType(account.getType());
        dto.setAmount(account.getAmount());
        dto.setId(account.getId());
        return dto;
    }

    public Account dtoToAccount(AccountDTO dto){
        Account account = new Account();
        account.setAlias(dto.getAlias());
        account.setType(dto.getType());
        account.setCbu(dto.getCbu());
        account.setAmount(dto.getAmount());
        return account;
    }
}
