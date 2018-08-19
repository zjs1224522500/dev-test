package tech.shunzi.testdev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto saveUser(User user) {
        return populate(userRepository.save(user));
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

    public UserDto populate(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setIntroduction(String.format("Hello, I am %s. And my id is %s. %s", userDto.getName(), userDto.getId(), user.getDesc()));
        userDto.setGroupNo(user.getId() % 10);
        return userDto;
    }

}
