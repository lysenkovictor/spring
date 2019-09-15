package ua.graduation.warehouse.service.entity.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductOwner {

    private int idProductOwner;
    private String firstName;
    private String lastName;
    private Optional<String> companyName;
    private Contacts contacts;

}
