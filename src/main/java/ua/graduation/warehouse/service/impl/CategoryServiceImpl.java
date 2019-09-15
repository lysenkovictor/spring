package ua.graduation.warehouse.service.impl;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.CategoryRepository;
import ua.graduation.warehouse.repository.model.CategoryEntity;
import ua.graduation.warehouse.service.CategoryService;
import ua.graduation.warehouse.service.entity.request.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

     private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(Category category) {

       CategoryEntity categoryEntity =  CategoryEntity.builder()
                .categoryName(category.getCategoryName())
                .build();

        categoryRepository.addCategory(categoryEntity);

    }
}
