package ua.graduation.warehouse.service.impl;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.*;
import ua.graduation.warehouse.service.ItemService;
import ua.graduation.warehouse.service.TypeOperation;
import ua.graduation.warehouse.service.entity.request.Category;
import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.entity.response.ItemResponse;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {


    public final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void addItem(Item item) {
        ItemEntity itemEntity = getFormItemEntity(item, TypeOperation.ADD);
        itemEntity.setCategories(
                item.getCategories() != null ? getCategoryEntity(item.getCategories()) : null
        );
        itemRepository.addItem(itemEntity);
    }

    @Override
    public void updateItem(Item item) {
        ItemEntity itemEntity = getFormItemEntity(item, TypeOperation.UPDATE);
        itemRepository.updateItem(itemEntity);
    }

    @Override
    public List<ItemResponse> getAllItemsOwnerBy(int idOwner) {
       List<ItemEntity> itemEntities  = itemRepository.getAllOwnerItemsBy(idOwner);

        return itemEntities.stream()
                .map(item-> ItemResponse.builder()
                        .itemId(item.getId())
                        .title(item.getTitle())
                        .productOwnerId(idOwner)
                        .price(item.getPrice())
                        .count(item.getCount())
                        .categories(item.getCategories().stream().map(el->Category.builder()
                                .categoryName(el.getCategoryName())
                                .id(el.getId()).build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

    }

    private ItemEntity getFormItemEntity(Item item, TypeOperation typeOperation) {
        LocalDateTime dateOperation =  LocalDateTime.now();

        OperationEntity operationEntity = OperationEntity.builder()
                .price(item.getPrice())
                .count(item.getCount())
                .dateOperation(dateOperation)
                .typeOperationEntity(TypeOperationEntity.builder().id(typeOperation.getType()).build())
                .build();

        ItemEntity itemEntity =  ItemEntity.builder()
                .productOwnerEntity(ProductOwnerEntity.builder().idProductOwner(item.getProductOwnerId()).build())
                .count(item.getCount())
                .id(item.getItemId())
                .dateAdd(LocalDateTime.now())
                .price(item.getPrice())
                .title(item.getTitle())
                .operationEntities(Collections.singletonList(operationEntity))
                .build();

        operationEntity.setItemEntity(itemEntity);

        return itemEntity;
    }

    private List<CategoryEntity> getCategoryEntity(List<Integer> categoryId) {
        return categoryId.stream().map(el->CategoryEntity.builder().id(el).build())
                .collect(Collectors.toList());
    }
}
