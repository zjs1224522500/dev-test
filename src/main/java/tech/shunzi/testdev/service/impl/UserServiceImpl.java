package tech.shunzi.testdev.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.shunzi.testdev.model.Address;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.AddressDto;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.publisher.EventPublisher;
import tech.shunzi.testdev.repo.AddressRepo;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.TestMultiInject;
import tech.shunzi.testdev.service.UserService;
import tech.shunzi.testdev.util.ObjectFieldEmptyUtil;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, TestMultiInject {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private EventPublisher eventPublisher;

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = populate(userDto);
        userRepository.save(user);
        eventPublisher.publishByPublisher(user);

        System.out.println(new Date() + "+++++++++++Save method go on");
        return populate(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            return populate(user);
        }).collect(Collectors.toList());
    }

    @Override
    public UserDto findSingleUser(int id) {
        return populate(userRepository.findById(id));
    }

    @Override
    public AddressDto findAddressDto(String userGuid) {
        AddressDto addressDto = new AddressDto();
        addressDto.setUserDto(populate(userRepository.getOne(userGuid)));
        return addressDto;
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto) {
        Address addressModel = new Address();
        String userGuid = addressDto.getUserDto().getGuid();
        User user = null;
        try {
            user = userRepository.getOne(userGuid);
        } catch (EntityNotFoundException | NullPointerException exception) {
            throw exception;
        }
        addressModel.setUser(user);
        addressRepo.save(addressModel);
        return findAddressDto(userGuid);
    }

    @Override
    @Transactional
    public List<UserDto> saveUserInBatchWithMethodTransaction(List<UserDto> userDtos) {
        return userDtos.stream().map(this::saveUser).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> saveUserInBatchWithoutTransaction(List<UserDto> userDtos) {
        return userDtos.stream().map(this::saveUser).collect(Collectors.toList());
    }

    public UserDto populate(User user) {
        UserDto userDto = new UserDto();
        if (null != user.getId()) {
            userDto.setId(user.getId());
            userDto.setGroupNo(user.getId() % 10);
        }
        userDto.setName(user.getName());
        userDto.setIntroduction(String.format("Hello, I am %s. And my id is %s. %s", userDto.getName(), userDto.getId(), user.getDesc()));
        return userDto;
    }

    public User populate(UserDto userDto) {
        List<String> whiteList = new ArrayList<>(1);
        whiteList.add("guid");
        whiteList.add("groupNo");
        List<String> emptyFieldsNames = ObjectFieldEmptyUtil.findEmptyFields(userDto, whiteList);
        if (CollectionUtils.isNotEmpty(emptyFieldsNames)) {
            throw new RuntimeException("Empty field :" + emptyFieldsNames.toString() + ". Please check the input.");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setDesc(userDto.getIntroduction());
        user.setId(userDto.getId());
        return user;
    }

}
