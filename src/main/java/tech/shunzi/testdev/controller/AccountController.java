package tech.shunzi.testdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.shunzi.testdev.model.Account;
import tech.shunzi.testdev.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        return new ResponseEntity(accountService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> save(@RequestBody Account account)
    {
        return new ResponseEntity(accountService.saveAccount(account), HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<Account> saveEvent(@RequestBody Account account)
    {
        return new ResponseEntity(accountService.saveAccountWithPublisher(account), HttpStatus.OK);
    }

}
