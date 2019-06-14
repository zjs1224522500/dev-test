package tech.shunzi.testdev.utils.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class FastJsonTest {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
    }

    @Test
    public void testParsePerson() throws IOException {
        String jsonString = "{\"name\":\"Elvis\",\"birthDay\":\"19971120\",\"bornTime\":\"1997-11-20 18:00:00\"}";
        System.out.println(objectMapper.readValue(jsonString, Person.class));
    }

    @Test
    public void testParsePersonWithWrongType() throws IOException {
        String jsonString = "{\"name\":\"Elvis\",\"birthDay\":19971120,\"bornTime\":\"1997-11-20 18:00:00\"}";
        System.out.println(objectMapper.readValue(jsonString, Person.class));
    }
}
