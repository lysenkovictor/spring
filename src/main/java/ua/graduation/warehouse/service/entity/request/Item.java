package ua.graduation.warehouse.service.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Item {

    private int itemId;
    private int count;
    private BigDecimal price;
    private String title;
    private int productOwnerId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateAdd;
    private List<Integer> categories;

}
