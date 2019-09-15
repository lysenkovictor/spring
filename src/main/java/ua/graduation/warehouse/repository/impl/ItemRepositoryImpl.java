package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.ItemEntity;


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


}
