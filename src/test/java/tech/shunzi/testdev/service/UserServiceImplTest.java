package tech.shunzi.testdev.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllUsers() {

        // Prepare data
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setDesc("DESC");
        user.setId(1111);
        user.setName("Shunzi");
        userList.add(user);

        List<UserDto> dtos = new ArrayList<>();
        UserDto dto = new UserDto();
        dto.setGroupNo(1);
        dto.setName(user.getName());
        dto.setId(user.getId());
        dto.setIntroduction("Hello, I am Shunzi. And my id is 1111. DESC");
        dtos.add(dto);

        // When
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> users = userService.findAllUsers();

        // Verify
        verify(userRepository).findAll();

        // Assert
        // override method equals() in UserDto class
        assertEquals(dtos, users);
    }

    @Test
    public void testGetUserById() {

        // prepare data
        int id = 1;
        User user = new User();
        user.setId(id);
        user.setName("shunzi");
        user.setDesc("desc");


        // when
        when(userRepository.findById(anyInt())).thenReturn(user);

        // Act
        UserDto userDto = userService.findSingleUser(id);

        // verify
        verify(userRepository).findById(anyInt());

        // assert
        assertEquals(id, userDto.getId());
    }
}
