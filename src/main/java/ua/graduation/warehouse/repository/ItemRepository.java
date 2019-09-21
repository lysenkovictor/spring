package ua.graduation.warehouse.repository;

import ua.graduation.warehouse.repository.model.ItemEntity;
import ua.graduation.warehouse.service.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterBetweenDate;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository {
    void addItem(ItemEntity itemEntity);
    void updateItem(ItemEntity itemEntity);

//    • provide list of all itemEntities for given product owner
    List<ItemEntity> getAllOwnerItemsBy(int idProductOwner);

    List<ItemEntity> getItemByTypeAndDateOperation(TypeOperation typeOperation, FilterBetweenDate filterBetweenDate);

}
