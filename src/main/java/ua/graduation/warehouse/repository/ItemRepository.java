package ua.graduation.warehouse.repository;

import ua.graduation.warehouse.repository.model.ItemEntity;
import ua.graduation.warehouse.service.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterBetweenDate;
import java.util.List;

public interface ItemRepository {
    void addItem(ItemEntity itemEntity);
    void updateItem(ItemEntity itemEntity);
    void updateItem(List<ItemEntity> itemEntity);
    List<ItemEntity> getAllOwnerItemsBy(int idProductOwner);
    List<ItemEntity> getItemBy(int idItem);
    List<ItemEntity> getListItemBy(List<Integer> idItem);
    List<ItemEntity> getItemByTypeAndDateOperation(TypeOperation typeOperation, FilterBetweenDate filterBetweenDate);
    List<ItemEntity> getAllItems();

}
