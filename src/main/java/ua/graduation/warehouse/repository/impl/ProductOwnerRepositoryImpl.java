package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ProductOwnerRepository;
import ua.graduation.warehouse.repository.model.ProductOwnerEntity;
import ua.graduation.warehouse.service.entity.request.ProductOwner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
@Repository
@Transactional
public class ProductOwnerRepositoryImpl implements ProductOwnerRepository {


    private final EntityManager entityManager;

    public ProductOwnerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public int addProductOwner(ProductOwnerEntity productOwnerEntity) {
        entityManager.persist(productOwnerEntity);
        return productOwnerEntity.getIdProductOwner();
    }

    @Override
    @Transactional
    public int removeProductOwner(int idProduct) {
        Query query = entityManager.createQuery("DELETE ProductOwnerEntity p WHERE p.id =:idProduct");
        query.setParameter("idProduct", idProduct);
        return query.executeUpdate();
    }

}
