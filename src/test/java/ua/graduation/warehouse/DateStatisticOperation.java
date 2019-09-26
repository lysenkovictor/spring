package ua.graduation.warehouse;

import lombok.Builder;
import lombok.Data;
import ua.graduation.warehouse.service.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterBetweenDate;

@Data
@Builder
public class DateStatisticOperation {
    TypeOperation typeOperation;
    FilterBetweenDate dateFilter;
}
