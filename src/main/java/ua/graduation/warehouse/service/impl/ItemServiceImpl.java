package ua.graduation.warehouse.service.impl;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.*;
import ua.graduation.warehouse.service.ItemService;
import ua.graduation.warehouse.service.entity.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {


    public final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void addItem(Item item) {

       LocalDateTime dateOperation =  LocalDateTime.now();
       Set<OperationEntity> operationEntities = new HashSet<>();

       OperationEntity operationEntity = OperationEntity.builder()
                .price(item.getPrice())
                .count(item.getCount())
                .dateOperation(dateOperation)
                .typeOperationEntity(TypeOperationEntity.builder().id(1).build())
                .build();

        operationEntities.add(operationEntity);


       Set<CategoryEntity> categoryEntities = null;
        if (item.getCategories() != null ) {
            categoryEntities = getCategoryEntity(item.getCategories());
        }

       ItemEntity itemEntity =  ItemEntity.builder()
                .productOwnerEntity(ProductOwnerEntity.builder().idProductOwner(item.getProductOwnerId()).build())
                .count(item.getCount())
                .dateAdd(LocalDateTime.now())
                .price(item.getPrice())
                .categories(categoryEntities)
                .operationEntities(operationEntities)
                .title(item.getTitle())
                .build();

        itemEntity.getOperationEntities().stream().forEach(el->el.setItemEntity(itemEntity));

        itemRepository.addItem(itemEntity);
    }


    @Override
    public void updateItem(Item item) {
        LocalDateTime dateOperation =  LocalDateTime.now();
        Set<OperationEntity> operationEntities = new HashSet<>();

        OperationEntity operationEntity = OperationEntity.builder()
                .price(BigDecimal.valueOf(2))
                .count(item.getCount())
                .dateOperation(dateOperation)
                .typeOperationEntity(TypeOperationEntity.builder().id(1).build())
                .build();

        operationEntities.add(operationEntity);

        ItemEntity itemEntity =  ItemEntity.builder()
                .productOwnerEntity(ProductOwnerEntity.builder().idProductOwner(item.getProductOwnerId()).build())
                .count(item.getCount())
                .id(item.getItemId())
                .dateAdd(LocalDateTime.now())
                .price(item.getPrice())
                .operationEntities(operationEntities)
                .title(item.getTitle())
                .build();

       // itemEntity.getOperationEntities().stream().forEach(el->el.setItemEntity(itemEntity));

        itemRepository.updateItem(itemEntity);


    }


    private Set<CategoryEntity> getCategoryEntity(List<Integer> categoryId) {
        return categoryId.stream().map(el->CategoryEntity.builder().id(el).build())
                .collect(Collectors.toSet());
    }
}
