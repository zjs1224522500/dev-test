> - 测试开发
> - 结合实际例子介绍相关测试框架的使用
> - 结合实际开发环境中测试开发的场景
> - 结合部分产品发布的标准以及相关Security Scan
> - Findbugs, Fortify，SonarQube，PMD也会做简单介绍
---
- 测试的重要性不做过多介绍。简单介绍一种 `TDD(Test Driven Development)`的模式。
- `TTD` 旨在通过先编写相关的测试类和测试代码来驱动实际业务代码的开发。对于测试代码，业务代码更多的像是在承担一个黑盒的角色，而测试代码只需要关注输入输出，从逻辑意义上保证输入输出和我们预期的结果相对应则是业务代码需要去保证实现的。
- `TDD`具体实现此处仅提供一种思路。测试类编写完成之后，使其一直运行在本地搭建的服务器上，编写相关业务代码完成后，通过在运行测试类的服务器上指定输入参数，通过测试代码的断言实时反应业务代码的正确性。
---
## 单元测试 Unit Test (UT)
- 应用场景：从代码层面去测试相关代码的正确性，是否符合预期的结果。此处不涉及和其他软件或者应用交互的过程，仅仅针对代码的逻辑处理。常常用于寻找程序中可能出现的异常以及代码逻辑的正确性。
- 工具：JUnit4/5, Mockito, SpringTest, PowerMock
#### JUnit4/5 
- [JUnit5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- JUnit 主要是利用 Java 注解构建了一套单元测试的相关体系，同时提供了很多工具方便单元测试的编写。具体注解的使用，参照官方文档。

#### Mockito
- [官方主页](https://site.mockito.org/)
#### How

##### service/dao unit test
- Before: You must init mockito in @Before of JUnit.
```Java
@Before
public void setUp() throws Exception
{
    MockitoAnnotations.initMocks(this);
}
```
- Annotation: `@Mock` `@InjectMocks`
    - `@Mock` : This annotation is used to mock an object. It seems like new Object() to give memory for this pointer.
    - `@InjectMocks` : This annotation is used to inject other mocks objects to the annotation decorated obejct.
- Method: `when()` `verify()` `any()`
    - `when()` : This method is used to listen the emthod if be invoked.And when this method is invoked, you can give return value for this method. So you do not need to care the specific implemention of this method.
    - `verify()`: It is used to verify if this method be invoked and you also can verify the invoke times as params of this method.
    - `any()`: It is used for mock objects. You can only give the type of the param without specific value. It is common used in `when` method to instead of specific params.
- Example: 

```Java
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception
    {
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
        // anyInt() will match all params whose type is int
        when(userRepository.findById(anyInt())).thenReturn(user);

        // Act
        UserDto userDto = userService.findSingleUser(id);

        // verify
        verify(userRepository).findById(anyInt());

        // assert
        assertEquals(id, userDto.getId());
    }
}
```
#### SpringTest
##### mockMvc unit test
- Before: You must init the MockMvc and bind it to controller class.
```Java
private HelloController helloController;
private MockMvc mockMvc;

@Before
public void setUp() throws Exception
{
    MockitoAnnotations.initMocks(this);
    //use controller to build mock mvc.
    mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
}
```
- Class : `MockMvc`
- Method : `perform()`,`get() post() put() delete()`, `contentType()`, `param()`, `andExpect()`,`status()`,`isOk()`,`andReturn()`,`getResponse()`, `getContentAsString()`
- There are some use cases of methods.
```Java
// GET
String responseStr = mockMvc.perform(get(requestUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//POST form                
String responseData = mockMvc.perform(post(requestURI).param("tenantId", "dev"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

//PUT
String responseData = mockMvc.perform(put(requestURI).param("tenantId", "dev")).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

//DELETE
String data = mockMvc .perform(delete(requestURI).contentType(MediaType.APPLICATION_JSON).content("the params with json format")
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
```

- Example:
```Java
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

```


#### PowerMock 
- We usually use PowerrMock to mock static method.`public static returnValue methodName(T param)`
##### How to use:
- Prepare: 
    - Use annotation `@PrepareForTest(IncludeStaticMethodClass.class)` to annotate unit test class.
    - Use annotation `@RunWith(PowerMockRunner.class)` to annotate unit test class.
- Use:
    - method: 
        - `PowerMockito.mockStatic()`  // mock static class
        - `PowerMocckito.when()` // mock static method invoke
- Example:
```Java
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
```
##### Caution:
- Please refer [PowerMock Github Repo](https://github.com/powermock/powermock) version issues.
- It must match `Mockito` version


## 集成测试 Integration Test (IT)
- 应用场景：比起单元测试，集成测试加载了相关的环境变量和上下文，能够对整个Spring上下文里的代码进行测试。而在单元测试中是通过`@Mock`的方式进行注入的，在集成测试的场景下可以直接 `@Autowire` 相关对象，同时能够和`DB`进行交互，也能对相关接口进行实际场景的测试。
- 工具: JUnit4/5, Spring Test, SpringBoot Test.

#### Spring Integration Test
- [官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)

#### Prepare:
- 添加 application-test.xml 配置文件，为专门的测试系统进行配置。
- 便于指定 `ActiveProfiles("test")`

#### 代码实例：
- BaseIntegrationTest
```Java
package tech.shunzi.testdev.integration.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUpEnv()
    {
        environmentVariables.set("key","value");
    }
}
```

- 其余具体的 `Integration Test Class`均可集成 `BaseIntegrationTest`
```Java
package tech.shunzi.testdev.integration.test;

import org.junit.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntTest extends BaseIntegrationTest {

    @Test
    public void testFindAll() throws Exception {
        // Prepare data
        String requestUrl = "/users";

        // Act
        String responseStr = mockMvc.perform(get(requestUrl).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        // Advise to use assert, sout in here is just to show query result from DB
        System.out.println(responseStr);
    }
}
```

- 针对 Service 和 Dao 层可以不加载相关 Web 环境
```Java
package tech.shunzi.testdev.integration.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tech.shunzi.testdev.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testFindAll() {
        System.out.println(userService.findAllUsers());
    }
}

```

## 系统测试 System Test (ST)
- 应用场景：系统测试相比于其他之前的测试，是从系统层面出发，对整个功能或者整套流程进行测试。是一种黑盒测试。也常常用来测试相关功能模块的性能。
- 工具： JMeter
### JMeter
- [官方主页](https://note.youdao.com/)
#### Download and Launch
- download JMeter: https://jmeter.apache.org/download_jmeter.cgi and select "apache-jmeter-4.0.zip"
- unzip the jmeter to your own folder
- go to your jmeter folder: [your folder]\apache-jmeter-4.0\bin and run jmeter.bat or run ApacheJMeter.jar

#### How to use JMeter
- [Short video](https://video.sap.com/media/t/1_4nj2lh3u/39197781)

#### Example Code
- [Github JMX](https://github.com/zjs1224522500/dev-test/blob/master/src/main/resources/jmx/Thread%20Group-%20User%20Function.jmx)

#### Head First
- Aim: Test Create User function
- Procedure: 
    - Query All User. Verify init data 
    - Create User And Verify Response Dara 
    - Query All User. Verify new added data

##### Step 1. Create Thread Group
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/new%20thread%20group.png)

##### Step 2. Define some common variables
- You can define some common data. Such as host, port and even params etc.
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/new%20http%20request.png)

##### Step 3. New Http Request
- Create Http Request Example
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/new%20http%20request.png)
- Http Request Main Param
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Request%20Body%20Param.png)
- You can control the header attribute (key-value)
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Header%20Attributes.png)
- JSON Extractor: Extract json response data to variables
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Header%20Attributes.png)

##### Step 4. New Asserters.(JSON Assertion, Response Assertion etc.)
- Assertion should be combined with Assertion Results.
- JSON Assertion
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/JSON%20Assertion.png)
- Response Assertion
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Response%20Assertion.png)

##### Step 5. Processor (Pre/Post)
- BeanShell Post Processor
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/Bean%20shell%20processor.png)

##### Step 6. Result Tree
- Result tree shows all result of test http request. 
![image](https://raw.githubusercontent.com/zjs1224522500/dev-test/master/src/main/resources/pic/result%20tree.png)



