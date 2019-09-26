package ua.graduation.warehouse.service.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ua.graduation.warehouse.service.entity.request.ProductOwner;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class StatisticInfoResponse {

    private BigDecimal totalCost;
    private int totalCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String typeOperation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    ProductOwner productOwner;
}
