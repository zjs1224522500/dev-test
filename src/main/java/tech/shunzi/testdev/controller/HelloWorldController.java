package tech.shunzi.testdev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class HelloWorldController {

    @PostMapping(value = "/dashboard")
    public String dashboard(HttpServletRequest request)
    {
        System.out.println(request.getContentType());
        return "success";
    }

    @PostMapping(value = "/dashboard/test")
    public String dashboardTest(@RequestBody String payload)
    {
        System.out.println(payload);
        return payload;
    }

    public static String read(HttpServletRequest request)
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


}
