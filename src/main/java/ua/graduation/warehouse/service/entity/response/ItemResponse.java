package ua.graduation.warehouse.service.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ua.graduation.warehouse.service.entity.request.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemResponse {

    private int itemId;
    private int count;
    private BigDecimal price;
    private String title;
    private int productOwnerId;
    private List<Category> categories;

}
