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

    @Override
    @Transactional
    public void updateItem(ItemEntity itemEntity) {

        String item = "UPDATE ItemEntity i set i.count=:count where id=:id";
        Query query = entityManager.createQuery(item);
        query.setParameter("count", itemEntity.getCount())
                .setParameter("id",itemEntity.getId());

        query.executeUpdate();

        entityManager.persist(itemEntity.getOperationEntities().iterator().next());
    }


    @Override
    @Transactional
    public void updateItem(List<ItemEntity> itemEntity) {

        String item = "UPDATE ItemEntity i set i.count=:count where id=:id";

        for (ItemEntity i: itemEntity) {
            entityManager.createQuery(item)
                    .setParameter("count", i.getCount())
                    .setParameter("id", i.getId())
                    .executeUpdate();

            entityManager.persist(i.getOperationEntities().iterator().next());
        }
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
    public List<ItemEntity> getItemBy(int idItem) {
        return entityManager.createQuery("SELECT i FROM ItemEntity i WHERE i.id =: id", ItemEntity.class)
                .setParameter("id",idItem).getResultList();
    }

    @Override
    public List<ItemEntity> getListItemBy(List<Integer> idItem) {
        Query query =entityManager.createQuery("SELECT i FROM ItemEntity i WHERE i.id IN (:listId)")
                .setParameter("listId",idItem);

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


    @Override
    public List<ItemEntity> getAllItems() {
        Query query = entityManager.createQuery(
                "SELECT i FROM ItemEntity i " +
                        "JOIN i.productOwnerEntity as own ");
        return query.getResultList();
    }


}
