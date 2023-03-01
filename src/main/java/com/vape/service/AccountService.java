package com.vape.service;

import com.vape.entity.Account;
import com.vape.repository.AccountRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountService  {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return accountRepository.saveAll(entities);
    }

    public Account getById(Long aLong) {
        return accountRepository.getById(aLong);
    }

    public <S extends Account> List<S> findAll(Example<S> example) {
        return accountRepository.findAll(example);
    }

    public <S extends Account> S save(S entity) {
        return accountRepository.save(entity);
    }

    public Optional<Account> findById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    public long count() {
        return accountRepository.count();
    }

    public void deleteById(Long aLong) {
        accountRepository.deleteById(aLong);
    }

    public void delete(Account entity) {
        accountRepository.delete(entity);
    }

    @Query(value = "SELECT * FROM account WHERE email=:email", nativeQuery = true)
    public Account getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }
}
