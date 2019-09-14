package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.ItemEntity;
import ua.graduation.warehouse.repository.model.OperationEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        OperationEntity operationEntity = itemEntity.getOperationEntities().iterator().next();
        operationEntity.setItemEntity(itemEntity);

        entityManager.persist(operationEntity);

    }


    @Override
    public List<ItemEntity> getAllOwnerItemsBy(int idProductOwner) {
        Query query = entityManager.createQuery(
                "SELECT i FROM ItemEntity i " +
                        "JOIN i.productOwnerEntity as pOwn " +
                        "WHERE pOwn.idProductOwner =: id");
        query.setParameter("id", idProductOwner);

        return query.getResultList();
    }


}
