package ua.graduation.warehouse.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contacts {
    private  List<Email> emails;
    private  List<Phone> phones;
}
