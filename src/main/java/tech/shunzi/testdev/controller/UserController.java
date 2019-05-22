package tech.shunzi.testdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.shunzi.testdev.model.Address;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.AddressDto;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.repo.AddressRepo;
import tech.shunzi.testdev.repo.UserTestRepository;
import tech.shunzi.testdev.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserTestRepository userTestRepository;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userTestRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        return new ResponseEntity<>(userTestRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDto> createAddress(AddressDto addressDto)
    {
        return new ResponseEntity<>(userService.addAddress(addressDto), HttpStatus.OK);
    }

    @GetMapping("/test-method-transaction")
    public ResponseEntity<List<UserDto>> createUsers()
    {
        return new ResponseEntity<>(userService.saveUserInBatchWithMethodTransaction(getUserDtos()), HttpStatus.OK);
    }

    @GetMapping("/test-no-transaction")
    public ResponseEntity<List<UserDto>> createUsersWithoutTrans()
    {
        return new ResponseEntity<>(userService.saveUserInBatchWithoutTransaction(getUserDtos()), HttpStatus.OK);
    }

    private List<UserDto> getUserDtos() {
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDtoOne = new UserDto();
        userDtoOne.setIntroduction("intro");
        userDtoOne.setName("name");
        userDtoOne.setId(12212);
        UserDto userDtoTwo = new UserDto();
        userDtoTwo.setIntroduction("intro");
        userDtoTwo.setName("name");
        userDtoList.add(userDtoOne);
        userDtoList.add(userDtoTwo);
        return userDtoList;
    }

}
