package ua.graduation.warehouse.service.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.graduation.warehouse.service.entity.request.ProductOwner;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StatisticInfoResponse {

    private BigDecimal totalCost;
    private int amount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String typeOperation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    ProductOwner productOwner;
}
