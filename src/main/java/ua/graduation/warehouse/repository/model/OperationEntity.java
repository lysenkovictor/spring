package ua.graduation.warehouse.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_operation")
@Data
@Builder
public class OperationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date_operation")
    private LocalDateTime dateOperation;

    //many this one TypeOperationEntity
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_operation_id")
    private TypeOperationEntity typeOperationEntity;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false, updatable = true, insertable = true)
    private ItemEntity itemEntity;

}
