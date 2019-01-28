package tech.shunzi.testdev.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.shunzi.testdev.model.Account;
import tech.shunzi.testdev.service.AccountService;

import java.io.IOException;
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
    public ResponseEntity<Account> saveEvent(@RequestBody Account account) throws InterruptedException {
        return new ResponseEntity(accountService.saveAccountWithPublisher(account), HttpStatus.OK);
    }

    @GetMapping("/alibaba")
    public ResponseEntity<JSONObject> testAlibabaJson()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "null");
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @GetMapping("/netsf")
    public net.sf.json.JSONObject testNetSfJson() throws IOException {
        net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
        net.sf.json.JSONObject jsonObject1 = new net.sf.json.JSONObject();
        jsonObject1.put("childKey", "null");
        jsonObject1.put("childKey2", "null2");
        jsonObject.put("child", jsonObject1);
        jsonObject.put("key", "null");
        jsonObject.put("key2", "null2");
        ObjectMapper mapper = new ObjectMapper();
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonObject.toString(), net.sf.json.JSONObject.class);
        //return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        //return jsonObject;
    }
}
