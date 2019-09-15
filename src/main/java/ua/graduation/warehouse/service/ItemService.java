package ua.graduation.warehouse.service;

import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.entity.response.ItemResponse;

import java.util.List;

public interface ItemService {
    void addItem(Item item);
    void updateItem(Item item);
    List<ItemResponse> getAllItemsOwnerBy(int idOwner);
}
