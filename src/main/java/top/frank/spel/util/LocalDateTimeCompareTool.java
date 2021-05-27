package top.frank.spel.util;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateTimeCompareTool {

    public int compareTimestampTz(ZonedDateTime dateOne, String dateTimeString){
        ZonedDateTime dateTime = ZonedDateTime.parse(dateTimeString, ISO_OFFSET_DATE_TIME);
        return dateOne.compareTo(dateTime);
    }

    public int compareTimestamp(LocalDateTime dateOne, String dateTimeString){
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, ISO_DATE_TIME);
        return dateOne.compareTo(dateTime);
    }
}
