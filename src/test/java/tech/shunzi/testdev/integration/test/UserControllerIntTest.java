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
        System.out.println(responseStr);
    }
}
