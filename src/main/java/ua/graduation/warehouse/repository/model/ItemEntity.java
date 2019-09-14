package ua.graduation.warehouse.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "t_item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn (name="product_owner_id")
    private ProductOwnerEntity productOwnerEntity;

    @Column(name = "date_add")
    private LocalDateTime dateAdd;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "t_category_has_t_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy="itemEntity")
    private Set<OperationEntity> operationEntities;

}
