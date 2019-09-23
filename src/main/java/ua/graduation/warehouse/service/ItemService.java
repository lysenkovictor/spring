package ua.graduation.warehouse.service;

import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.entity.request.ItemStatisticInfo;
import ua.graduation.warehouse.service.entity.response.ItemResponse;
import ua.graduation.warehouse.service.entity.response.StatisticInfoResponse;

import java.util.List;

public interface ItemService {
    void addItem(Item item);
    void addToCurrentItem(Item item);

    void withdraw(List<Item> itemList);

    List<ItemResponse> getAllItemsOwnerBy(int idOwner);
    StatisticInfoResponse getStatisticInformationAboutAmountAndTotalCostItems(ItemStatisticInfo itemStatisticInfo);
    List<StatisticInfoResponse> getTotalCostItemsTopProductOwner();

}
