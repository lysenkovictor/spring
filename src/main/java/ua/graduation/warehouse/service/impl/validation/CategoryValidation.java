package ua.graduation.warehouse.service.impl.validation;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.CategoryRepository;
import ua.graduation.warehouse.repository.model.CategoryEntity;
import ua.graduation.warehouse.service.impl.exeption.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryValidation {

    private final CategoryRepository categoryRepository;

    public CategoryValidation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void checkCategoryIsPresent(List<Integer> listIdCategory) {
        List<CategoryEntity> categoryEntities = categoryRepository.getListCategoryBy(listIdCategory);
        List<Integer> listSearchIdCategory =  categoryEntities.stream()
                .map(CategoryEntity::getId).collect(Collectors.toList());
        checkElementIsPresent(listSearchIdCategory, listIdCategory);

    }

    private void checkElementIsPresent(List<Integer> listIdCurrent, List<Integer> list) {

        ArrayList<Integer> idNotFound = new ArrayList();
        for (int id : list) {
            if (!listIdCurrent.contains(id)) {
                idNotFound.add(id);
            }
        }
        if (!idNotFound.isEmpty()) {
            throw new ItemNotFoundException(String.format("%s - category not found", idNotFound));
        }
    }

}
