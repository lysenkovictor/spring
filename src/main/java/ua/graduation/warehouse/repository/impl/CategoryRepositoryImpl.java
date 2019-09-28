package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.CategoryRepository;
import ua.graduation.warehouse.repository.model.CategoryEntity;
import ua.graduation.warehouse.repository.model.ItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final EntityManager entityManager;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addCategory(CategoryEntity categoryEntity) {
         entityManager.persist(categoryEntity);
    }

    @Override
    public List<CategoryEntity> getListCategoryBy(List<Integer> idCategory) {
        Query query = entityManager.createQuery("SELECT i FROM CategoryEntity i WHERE i.id IN (:listId)")
                .setParameter("listId", idCategory);
        return query.getResultList();
    }
}
