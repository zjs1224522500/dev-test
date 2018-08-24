package tech.shunzi.testdev.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.impl.UserServiceImpl;
import tech.shunzi.testdev.util.ObjectFieldEmptyUtil;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@PrepareForTest(ObjectFieldEmptyUtil.class)
@RunWith(PowerMockRunner.class)
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
        Integer id = 1;
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

    @Test(expected = RuntimeException.class)
    public void testSaveUser() {

        // Prepare data
        UserDto user = new UserDto();
        user.setName("shunzi");
        user.setIntroduction("intro");
        User model = new User();
        model.setDesc("intro");
        model.setName("shunzi");
        model.setId(1);
        List<String> stringList = new ArrayList<>();
        stringList.add("name");

        PowerMockito.mockStatic(ObjectFieldEmptyUtil.class);
        PowerMockito.when(ObjectFieldEmptyUtil.findEmptyFields(anyObject(), anyList())).thenReturn(stringList);

        // Act
        UserDto userDto = userService.saveUser(user);

        // Assert
        assertEquals("shunzi", userDto.getName());
    }
}
