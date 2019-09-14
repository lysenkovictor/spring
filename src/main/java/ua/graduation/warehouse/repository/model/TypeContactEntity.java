package ua.graduation.warehouse.repository.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_type_contact")
@Data
@Builder
public class TypeContactEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "typeContactEntity", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<EmailEntity> emailEntities;

    @OneToMany(mappedBy = "typeContactEntity", fetch = FetchType.LAZY)
    private List<PhoneEntity> phoneEntities;


}
