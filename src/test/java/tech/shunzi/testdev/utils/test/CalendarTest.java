package tech.shunzi.testdev.utils.test;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;


public class CalendarTest {

    @Test
    public void testCalendar()
    {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        LocalDate date1 = LocalDate.now();
        System.out.println(date1);
    }

    @Test
    public void testJava8Date()
    {
        System.out.println(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
