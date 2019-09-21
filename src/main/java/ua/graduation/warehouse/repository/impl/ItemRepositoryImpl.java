package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.ItemEntity;
import ua.graduation.warehouse.service.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterBetweenDate;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ItemRepositoryImpl implements ItemRepository {

    private final EntityManager entityManager;

    public ItemRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addItem(ItemEntity itemEntity) {
        entityManager.persist(itemEntity);
    }


    @Transactional
    public void updateItem(ItemEntity itemEntity) {

        String item = "UPDATE ItemEntity i set i.count=:count where id=:id";
        Query query = entityManager.createQuery(item);
        query.setParameter("count", itemEntity.getCount())
                .setParameter("id",itemEntity.getId());

        query.executeUpdate();
        entityManager.persist(itemEntity.getOperationEntities().iterator().next());
    }


    public List<ItemEntity> getAllOwnerItemsBy2(int idProductOwner) {
        Query query = entityManager.createQuery(
                "SELECT i FROM ItemEntity i " +
                        "JOIN i.productOwnerEntity as pOwn " +
                        "WHERE pOwn.idProductOwner =: id");
        query.setParameter("id", idProductOwner);

        return query.getResultList();
    }

    @Override
    public List<ItemEntity> getAllOwnerItemsBy(int idProductOwner) {
        Query query = entityManager.createQuery(
                "SELECT i FROM ItemEntity i " +
                        "JOIN i.productOwnerEntity as own " +
                        "LEFT JOIN FETCH i.categories as c " +
                        "WHERE own.idProductOwner =: id");
        query.setParameter("id", idProductOwner);

        return query.getResultList();
    }

    @Override
    public List<ItemEntity> getItemByTypeAndDateOperation(TypeOperation typeOperation, FilterBetweenDate filterBetweenDate) {
        Query query = entityManager.createQuery(
                "SELECT i FROM ItemEntity i " +
                "JOIN i.operationEntities as op " +
                "JOIN op.typeOperationEntity as t_op " +
                "wHERE t_op.id =: typeOperationId");
        query.setParameter("typeOperationId", typeOperation.getTypeId());

        return query.getResultList();
    }

//
//
//    select  *  from service.t_item as i
//    inner JOIN service.t_operation as o
//    on i.id = o.item_id
//    where o.type_operation_id = '2' and
//    date_operation between '2019-09-15 00:00' and '2019-09-15 23:59:59.999999999'




//    select  *  from service.t_item as i
//    inner JOIN service.t_operation as o
//    on i.id = o.item_id
//    inner JOIN service.t_type_operation as tOp
//    on o.type_operation_id = tOp.id
//    where tOp.id = '2' and date_operation between '2019-09-15 00:00' and '2019-09-15 23:59:59.999999999'


}
