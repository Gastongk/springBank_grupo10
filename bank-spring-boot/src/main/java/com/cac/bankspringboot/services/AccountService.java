package com.cac.bankspringboot.services;

import com.cac.bankspringboot.exceptions.UserNotExistsException;
import com.cac.bankspringboot.mappers.AccountMapper;
import com.cac.bankspringboot.mappers.UserMapper;
import com.cac.bankspringboot.models.Account;
import com.cac.bankspringboot.models.User;
import com.cac.bankspringboot.models.dtos.AccountDTO;
import com.cac.bankspringboot.models.dtos.UserDTO;
import com.cac.bankspringboot.models.dtos.enums.AccountType;
import com.cac.bankspringboot.repositories.AccountRepository;
import jakarta.persistence.AccessType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }
    public List<AccountDTO> getAccounts() {
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO dto) {
        dto.setType(AccountType.SAVINGS_BANKS);
        dto.setAmount(BigDecimal.ZERO);
        Account newAccount  = repository.save(AccountMapper.dtoToAccount(dto));
        return AccountMapper.accountToDto(newAccount);
    }

    public AccountDTO getAccountById(Long id) {
        Account entity = repository.findById(id).get();
        return AccountMapper.accountToDto(entity);
    }

    public String deleteAccount(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "la cuenta con id: " + id + " ha sido eliminada";
        } else {
            throw new UserNotExistsException("La cuenta a eliminar elegida no existe");
        }
    }

    public AccountDTO updateAccount (Long id, AccountDTO dto) {
        Account AccountToModify = repository.findById(id).get();

        if (repository.existsById(id)) {
            if (dto.getAlias() != null) {
                AccountToModify.setAlias(dto.getAlias());
            }
            if (dto.getType() != null) {
                AccountToModify.setType(dto.getType());
            }
            if (dto.getCbu() != null) {
                AccountToModify.setCbu(dto.getCbu());
            }
            if (dto.getAmount() != null) {
                AccountToModify.setAmount(dto.getAmount());
            }

            Account AccountModified = repository.save(AccountToModify);
            return AccountMapper.accountToDto(AccountModified);
        }
        return null;
    }
}
