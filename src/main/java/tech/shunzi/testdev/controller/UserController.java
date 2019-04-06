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
}
