package ua.graduation.warehouse.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatterDate {
    public static String getDateMinFormatter(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN).format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static String getDateMaxFormatter(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX).format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
