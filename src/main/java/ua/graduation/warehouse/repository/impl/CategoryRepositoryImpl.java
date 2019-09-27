package ua.graduation.warehouse.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.CategoryRepository;
import ua.graduation.warehouse.repository.model.CategoryEntity;

import javax.persistence.EntityManager;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final EntityManager entityManager;

    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean addCategory(CategoryEntity categoryEntity) {
        entityManager.persist(categoryEntity);
        return false;
    }
}
