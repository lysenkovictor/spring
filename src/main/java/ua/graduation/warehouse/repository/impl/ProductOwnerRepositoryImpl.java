package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ProductOwnerRepository;
import ua.graduation.warehouse.repository.model.ProductOwnerEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class ProductOwnerRepositoryImpl implements ProductOwnerRepository {


    private final EntityManager entityManager;

    public ProductOwnerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int addProductOwner(ProductOwnerEntity productOwnerEntity) {
        entityManager.persist(productOwnerEntity);
        return productOwnerEntity.getIdProductOwner();
    }

    @Override
    public int removeProductOwner(int idOwner) {
        Query query = entityManager.createQuery("DELETE ProductOwnerEntity p WHERE p.id =:idProduct");
        query.setParameter("idProduct", idOwner);
        return query.executeUpdate();
    }

    @Override
    public List<ProductOwnerEntity> getSingletonListProductOwner(int idOwner) {
        return entityManager.createQuery("SELECT own FROM ProductOwnerEntity own " +
                "WHERE own.id =:idOwe")
                .setParameter("idOwe",idOwner)
                .getResultList();
    }
}
