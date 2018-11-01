package tech.shunzi.testdev.service;

import tech.shunzi.testdev.model.Account;

import java.util.List;

public interface AccountService {

    Account saveAccount(Account account);

    List<Account> findAll();

    Account saveAccountWithPublisher(Account account) throws InterruptedException;
}
