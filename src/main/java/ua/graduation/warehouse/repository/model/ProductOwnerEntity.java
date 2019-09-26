package ua.graduation.warehouse.repository.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "t_product_owner")
@ToString
public class ProductOwnerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idProductOwner;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company_name")
    private String companyName;

    @OneToMany(mappedBy = "productOwnerEntity")
    private Set<ItemEntity> itemEntities;

    @OneToMany(mappedBy = "productOwnerEntity")
    private List<EmailEntity> emailEntities;

    @OneToMany(mappedBy = "productOwnerEntity", cascade = CascadeType.ALL)
    private List<PhoneEntity> phoneEntities;


}
