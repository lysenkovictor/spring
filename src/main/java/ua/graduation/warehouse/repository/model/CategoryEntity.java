package ua.graduation.warehouse.repository.model;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "t_category")
public class CategoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String categoryName;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "t_category_has_t_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<ItemEntity> itemEntities;
}
