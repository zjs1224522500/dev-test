package tech.shunzi.testdev.service;

import tech.shunzi.testdev.model.Address;
import tech.shunzi.testdev.model.dto.AddressDto;
import tech.shunzi.testdev.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

    UserDto findSingleUser(int id);

    AddressDto findAddressDto(String userGuid);

    AddressDto addAddress(AddressDto addressDto);
}
