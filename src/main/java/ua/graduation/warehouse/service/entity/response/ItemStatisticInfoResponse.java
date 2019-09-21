package ua.graduation.warehouse.service.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemStatisticInfoResponse {
    private BigDecimal totalCost;
    private int amount;
    private String typeOperation;
}
