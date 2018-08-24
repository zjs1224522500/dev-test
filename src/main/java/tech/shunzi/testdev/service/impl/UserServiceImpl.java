package tech.shunzi.testdev.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.UserService;
import tech.shunzi.testdev.util.ObjectFieldEmptyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = populate(userDto);
        userRepository.save(user);
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
