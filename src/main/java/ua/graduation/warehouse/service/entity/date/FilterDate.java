package ua.graduation.warehouse.service.entity.date;

import ua.graduation.warehouse.utils.FormatterDate;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public enum FilterDate {

    CURRENT_DAY {
        public FilterBetweenDate getPeriod() {
          return   FilterBetweenDate.builder()
                    .dateStringFrom(FormatterDate.getDateMinFormatter(LocalDate.now()))
                    .dateStringTo(FormatterDate.getDateMinFormatter(LocalDate.now()))
                    .build();
        }
    },

    CURRENT_MONTH {
        public FilterBetweenDate getPeriod() {
            return FilterBetweenDate.builder()
                    .dateStringFrom(FormatterDate.getDateMinFormatter(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())))
                    .dateStringTo(FormatterDate.getDateMaxFormatter(LocalDate.now()))
                    .build();
        }
    },

    LAST_MONTH {
        public FilterBetweenDate getPeriod() {
            return FilterBetweenDate.builder()
                    .dateStringFrom(FormatterDate.getDateMinFormatter(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth())))
                    .dateStringTo(FormatterDate.getDateMaxFormatter(LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth())))
                    .build();
        }
    };

    public abstract FilterBetweenDate getPeriod();

}