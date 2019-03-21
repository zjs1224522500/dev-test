package tech.shunzi.testdev.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
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
import org.springframework.format.annotation.NumberFormat;
import org.springframework.util.NumberUtils;
import tech.shunzi.testdev.model.User;
import tech.shunzi.testdev.model.dto.UserDto;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.impl.UserServiceImpl;
import tech.shunzi.testdev.util.ObjectFieldEmptyUtil;

import javax.xml.stream.Location;
import java.util.*;

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

    @Test
    public void testConvertTime() {
        User user = new User();
        user.setName("Elvis");
        user.setId(1);
        user.setDesc("desc");
        user.setGuid("guid");

        long start = System.currentTimeMillis();
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(user));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        long start2 = System.currentTimeMillis();
        Map<Object, Object> map = new BeanMap(user);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
        System.out.println(map.get("name"));
    }

    @Test
    public void testDateToString() {
        Date date = new Date();
        System.out.println(date.getTime());
        System.out.println(date);
        System.out.println(date.toString());
        System.out.println(org.apache.commons.lang3.math.NumberUtils.toDouble(String.valueOf(date.getTime())));
    }

    @Test
    public void convertExtension() {
        String s = "N_EX_13";
        s = s.replace("_", "");//NEX13
        int flagIndex = s.indexOf("E");
        s = (new StringBuilder()).append(s.substring(0, flagIndex).toLowerCase()).append("E").append(s.substring(flagIndex + 1).toLowerCase()).toString();
        System.out.println(s);
    }

    @Test
    public void test() {
        Scanner in = new Scanner(System.in);
        String location = in.nextLine();
        String[] xy = location.split(",");
        Double pX = Double.valueOf(xy[0]);
        Double pY = Double.valueOf(xy[1]);
        String range = in.nextLine();
        String[] rangeXy = range.split(",");
        List<Double[]> locations = new ArrayList<>();
        for (int i = 0; i < rangeXy.length; ) {
            if (i % 2 == 0) {
                Double x = Double.valueOf(rangeXy[i]);
                Double y = Double.valueOf(rangeXy[i + 1]);
                Double[] edge = {x, y};
                locations.add(edge);
            }
            i += 2;
        }
        System.out.println(pX + "   " + pY);
        System.out.println(locations);

    }

    @Test
    public void testNumberFormat() {
        String one = "123,456,789.02";
        String two = "123.456.789,02";

        String format = one.replaceAll(",", "!").replaceAll("\\.", ",").replaceAll("!", ".");
        assertEquals(format, two);
    }

    @Test
    public void testAdd()
    {
        for (int i = 0; i < 10; i++)
        {
            System.out.println("i : " + i);
            for (int index = i; index < 5; index ++) {
                System.out.println("index : " + index);
            }
        }
    }

}
