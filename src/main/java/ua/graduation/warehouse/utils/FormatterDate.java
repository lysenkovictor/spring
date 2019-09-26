package ua.graduation.warehouse.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class FormatterDate {

    public static LocalDateTime getDateMinFormatter(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime getDateMaxFormatter(LocalDate localDate) {

        return LocalDateTime.of(localDate, LocalTime.MAX);
    }


}
