package ua.graduation.warehouse.repository.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_email")
@Data
@Builder
public class EmailEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "emeil")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_contact_id")
    private TypeContactEntity typeContactEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_owner_id")
    private ProductOwnerEntity productOwnerEntity;

}
