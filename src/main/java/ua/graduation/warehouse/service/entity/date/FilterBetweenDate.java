package ua.graduation.warehouse.service.entity.date;

import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Builder
public class FilterBetweenDate {
   public LocalDateTime dateStringFrom;
   public LocalDateTime dateStringTo;





}
