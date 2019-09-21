package ua.graduation.warehouse.service;

import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.entity.request.ItemStatisticInfo;
import ua.graduation.warehouse.service.entity.response.ItemResponse;
import ua.graduation.warehouse.service.entity.response.ItemStatisticInfoResponse;

import java.util.List;

public interface ItemService {
    void addItem(Item item);
    void updateItem(Item item);
    List<ItemResponse> getAllItemsOwnerBy(int idOwner);
    ItemStatisticInfoResponse getStatisticInformationAboutAmountAndTotalCostItems(ItemStatisticInfo itemStatisticInfo);

}
