package tech.shunzi.testdev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.service.impl.UserServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void test() {
        Logger logger = LoggerFactory.getLogger(getClass());
        log(logger, null, "query channel", true);
    }

    public static void log(Logger logger, String transactionGuid, String message, boolean isStart) {
        String phase = isStart ? "start" : "stop";
        logger.warn("Thread[{}] {} to {} in transaction[{}] on time {} !", Thread.currentThread().getId(), phase, message, transactionGuid, System.currentTimeMillis());
    }

    @Test
    public void testSubString() {
        String fileName = "stable";
        List<String> files = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            files.add(fileName + i);
        }

        long start = System.currentTimeMillis();
        files.stream().forEach(file -> {
            System.out.println(file.toString().replace("stable", ""));
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        long start2 = System.currentTimeMillis();
        files.stream().forEach(file -> {
            System.out.println(file.toString().substring(6));
        });
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }


    @Test
    public void testDateTime()
    {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssZ");
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println(sdf.format(ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.UTC)));
    }
}
