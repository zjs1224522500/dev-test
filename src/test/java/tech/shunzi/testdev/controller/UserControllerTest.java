package tech.shunzi.testdev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        // Prepare data
        String requestUrl = "/users";
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setName("shunzi");
        userDtoList.add(userDto);

        ObjectMapper mapper = new ObjectMapper();
        String expectedValue = mapper.writeValueAsString(userDtoList);

        // When
        when(userService.findAllUsers()).thenReturn(userDtoList);

        // Act
        String responseStr = mockMvc.perform(get(requestUrl).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Verify
        verify(userService).findAllUsers();

        // Assert
        assertEquals(expectedValue, responseStr);
    }
}
