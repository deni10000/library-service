package ru.deni1000.accountservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.deni1000.accountservice.dto.AccountDto;
import ru.deni1000.accountservice.dto.CreateAccountRequest;
import ru.deni1000.accountservice.mapper.AccountMapper;
import ru.deni1000.accountservice.model.Account;
import ru.deni1000.accountservice.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(accountMapper::toDto)
                .toList();
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Account newAccount = accountMapper.toEntity(createAccountRequest);

        newAccount = accountRepository.save(newAccount);
        return accountMapper.toDto(newAccount);
    }

    public AccountDto getUser(long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));

        return accountMapper.toDto(account);
    }

    public void deleteUser(long accountId) {
        accountRepository.deleteById(accountId);
    }
} 