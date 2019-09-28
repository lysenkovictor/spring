package ua.graduation.warehouse.repository;

import ua.graduation.warehouse.repository.model.CategoryEntity;
import java.util.List;

public interface CategoryRepository {
    void addCategory(CategoryEntity categoryEntity);
    List<CategoryEntity> getListCategoryBy(List<Integer> idCategory);

}
