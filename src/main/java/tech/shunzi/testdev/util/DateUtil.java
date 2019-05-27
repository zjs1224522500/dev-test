package tech.shunzi.testdev.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

    private static final BigDecimal SECOND = new BigDecimal(1000);
    private static final BigDecimal MINUTE = new BigDecimal(60);
    private static final BigDecimal HOUR = new BigDecimal(60);
    private static final BigDecimal DAY = new BigDecimal(24);
    private static final BigDecimal MILLSECOND_OF_ONE_DAY = SECOND.multiply(MINUTE).multiply(HOUR).multiply(DAY);
    private static final String GENERAL_DATE_PATYTERN = "yyyyMMdd";

    public static long getTimestampOfToday() {
        return LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * UTC+8 Convert the date
     * @return
     */
    public static long getTimestampOfNow() {
        return LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parseDate(String dateText, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateText);
    }

    public static Date parseDate(long timestamp) {
        return new Date(timestamp);
    }

    public static String formatDate(long timestamp, String pattern) {
        return formatDate(parseDate(timestamp), pattern);
    }
}
