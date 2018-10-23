package tech.shunzi.testdev.integration.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tech.shunzi.testdev.repo.UserRepository;
import tech.shunzi.testdev.service.TestMultiInject;
import tech.shunzi.testdev.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TestMultiInject testMultiInject;

    @Test
    public void testFindAll() {
        System.out.println(userService.findAllUsers());
    }

    @Test
    public void testInject()
    {
        testMultiInject.test();
    }
}
