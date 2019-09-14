package ua.graduation.warehouse.repository.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_type_operation")
@Data
@Builder
public class TypeOperationEntity {

    @Id
    public Integer id;

    @Column(name = "type")
    private String type;

    @JsonManagedReference
    @OneToMany(mappedBy = "typeOperationEntity", fetch = FetchType.LAZY)
    private Set<OperationEntity> operationEntities;

}
