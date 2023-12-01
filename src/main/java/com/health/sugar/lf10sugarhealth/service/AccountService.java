package com.health.sugar.lf10sugarhealth.service;


import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.common.exceptions.AccountNotFoundException;
import com.health.sugar.lf10sugarhealth.model.Account;
import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import com.health.sugar.lf10sugarhealth.repository.AccountRepository;
import com.health.sugar.lf10sugarhealth.repository.MembershipStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService {
    @Autowired
    MembershipStatusRepository membershipStatusRepository;

    @Autowired
    AccountRepository accountRepository;

    public Account getByAccountId(UUID account_id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(account_id);

        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new AccountNotFoundException("no membership for profile found");
        }
    }

    public Boolean isAccountAlreadyCreated(String login_uid) {
        Optional<Account> accountOptional = accountRepository.findAccountByLoginUid(login_uid);
        return accountOptional.isPresent();
    }


    public Account getByLoginId(String login_id) {
        Optional<Account> accountOptional = accountRepository.findAccountByLoginUid(login_id);

        return accountOptional.orElse(null);
    }

    public List<Account> getAll() throws EmptyResponseException {
        List<Account> accountList = accountRepository.findAll();

        if (accountList.isEmpty()) {
            throw new EmptyResponseException("Account response is empty");
        }
        return accountList;
    }


    public Account create(String displayName, String login_uid) throws DataIntegrityViolationException {
        MembershipStatus membershipStatus = membershipStatusRepository.save(new MembershipStatus());

        Account account = accountRepository.save(
                new Account(displayName, membershipStatus, login_uid));

        membershipStatusRepository.save(membershipStatus);
        return account;
    }

    public void delete(UUID account_id) {
        accountRepository.deleteById(account_id);
    }


}
