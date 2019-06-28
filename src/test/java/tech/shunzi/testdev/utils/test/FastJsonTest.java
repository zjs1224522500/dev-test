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

    @Test
    public void testParseA() throws IOException {
        String jsonString = "{\"code\":0,\"message\":\"成功！\",\"data\":{\"code\":0,\"pageIndex\":1,\"total\":34,\"data\":[{\"logId\":null,\"id\":\"1a0f2b91-6ad8-4197-827b-c9e1922afbb4\",\"isDeleted\":0,\"addTime\":\"2019-06-25T02:28:11.000+0000\",\"addAccountId\":\"scil_1\",\"addAccountName\":\"超级管理员\",\"editTime\":null,\"editAccountId\":null,\"editAccountName\":null,\"organizationId\":null,\"organizationCode\":\"Test\",\"organizationName\":\"TestShipper\",\"topOrganizationId\":null,\"topOrganizationCode\":null,\"topOrganizationName\":null,\"parentId\":null,\"contacts\":\"TestShipper\",\"contactsPhone\":\"13508163660\",\"accountSource\":1,\"auditState\":1,\"state\":0,\"auditAccountId\":\"scil_1\",\"auditAccountName\":\"超级管理员\",\"auditTime\":\"2019-06-25T02:28:11.000+0000\",\"auditReason\":null},{\"logId\":null,\"id\":\"dd30134f-cde1-4c14-867b-63d04c60b339\",\"isDeleted\":0,\"addTime\":\"2019-06-24T06:29:36.000+0000\",\"addAccountId\":\"scil_1\",\"addAccountName\":\"超级管理员\",\"editTime\":null,\"editAccountId\":null,\"editAccountName\":null,\"organizationId\":null,\"organizationCode\":\"tx0624\",\"organizationName\":\"tt0624001\",\"topOrganizationId\":null,\"topOrganizationCode\":null,\"topOrganizationName\":null,\"parentId\":null,\"contacts\":\"tt0624001\",\"contactsPhone\":\"18345612222\",\"accountSource\":1,\"auditState\":1,\"state\":0,\"auditAccountId\":\"scil_1\",\"auditAccountName\":\"超级管理员\",\"auditTime\":\"2019-06-24T06:29:36.000+0000\",\"auditReason\":null},{\"logId\":null,\"id\":\"09e2ce4c-a5d7-4d46-b594-0016ed53aa0b\",\"isDeleted\":0,\"addTime\":\"2019-06-24T03:38:53.000+0000\",\"addAccountId\":\"scil_1\",\"addAccountName\":\"超级管理员\",\"editTime\":null,\"editAccountId\":null,\"editAccountName\":null,\"organizationId\":null,\"organizationCode\":\"Chasen\",\"organizationName\":\"Chasen\",\"topOrganizationId\":null,\"topOrganizationCode\":null,\"topOrganizationName\":null,\"parentId\":null,\"contacts\":\"Chasen\",\"contactsPhone\":\"18999999999\",\"accountSource\":1,\"auditState\":1,\"state\":0,\"auditAccountId\":\"scil_1\",\"auditAccountName\":\"超级管理员\",\"auditTime\":\"2019-06-24T03:38:53.000+0000\",\"auditReason\":null},{\"logId\":null,\"id\":\"4b4041a0-2352-4982-b144-99542d89a773\",\"isDeleted\":0,\"addTime\":\"2019-06-24T03:31:57.000+0000\",\"addAccountId\":\"scil_1\",\"addAccountName\":\"超级管理员\",\"editTime\":null,\"editAccountId\":null,\"editAccountName\":null,\"organizationId\":null,\"organizationCode\":\"tt0624\",\"organizationName\":\"tt0624\",\"topOrganizationId\":null,\"topOrganizationCode\":null,\"topOrganizationName\":null,\"parentId\":null,\"contacts\":\"tt0624\",\"contactsPhone\":\"18180440971\",\"accountSource\":1,\"auditState\":1,\"state\":0,\"auditAccountId\":\"scil_1\",\"auditAccountName\":\"超级管理员\",\"auditTime\":\"2019-06-24T03:31:57.000+0000\",\"auditReason\":null},{\"logId\":null,\"id\":\"b724dc61-9f1e-42ca-a6c0-66ff0612b83b\",\"isDeleted\":0,\"addTime\":\"2019-06-21T07:42:42.000+0000\",\"addAccountId\":\"scil_1\",\"addAccountName\":\"超级管理员\",\"editTime\":null,\"editAccountId\":null,\"editAccountName\":null,\"organizationId\":null,\"organizationCode\":\"tt2uuu\",\"organizationName\":\"tt0621\",\"topOrganizationId\":null,\"topOrganizationCode\":null,\"topOrganizationName\":null,\"parentId\":null,\"contacts\":\"tt0621\",\"contactsPhone\":\"13980042222\",\"accountSource\":1,\"auditState\":1,\"state\":0,\"auditAccountId\":\"scil_1\",\"auditAccountName\":\"超级管理员\",\"auditTime\":\"2019-06-21T07:42:42.000+0000\",\"auditReason\":null}]}}";
        System.out.println(objectMapper.readValue(jsonString, A.class));
    }
}
