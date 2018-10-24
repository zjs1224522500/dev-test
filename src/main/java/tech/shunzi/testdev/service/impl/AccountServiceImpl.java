package tech.shunzi.testdev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.shunzi.testdev.model.Account;
import tech.shunzi.testdev.publisher.AccountEventPublisher;
import tech.shunzi.testdev.repo.AccountRepository;
import tech.shunzi.testdev.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountEventPublisher publisher;

    @Override
    public Account saveAccount(Account account) {
        return save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account saveAccountWithPublisher(Account account)
    {
        Account accountResult = save(account);
        publisher.publishEventByContext(account);
        publisher.publishEventByPublisher(account);
        publisher.publishApplicationEventByContext(account);
        publisher.publishApplicationEventByPublisher(account);
        return accountResult;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account save(Account account)
    {
        return accountRepository.save(account);
    }
}
