package ua.graduation.warehouse.repository.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "t_type_operation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeOperationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;

    @Column(name = "type")
    private String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "typeOperationEntity", fetch = FetchType.LAZY)
    private Collection<OperationEntity> operationEntities;

}
