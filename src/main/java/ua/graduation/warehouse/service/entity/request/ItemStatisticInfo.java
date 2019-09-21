package ua.graduation.warehouse.service.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemStatisticInfo {

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String period;
    private String typeOperation;
}
