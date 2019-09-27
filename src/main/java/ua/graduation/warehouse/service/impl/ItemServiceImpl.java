package ua.graduation.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.*;
import ua.graduation.warehouse.service.ItemService;
import ua.graduation.warehouse.service.catalog.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterBetweenDate;
import ua.graduation.warehouse.service.entity.date.FilterDate;
import ua.graduation.warehouse.service.entity.request.Category;
import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.entity.request.ItemStatisticInfo;
import ua.graduation.warehouse.service.entity.request.ProductOwner;
import ua.graduation.warehouse.service.entity.response.ItemResponse;
import ua.graduation.warehouse.service.entity.response.StatisticInfoResponse;
import ua.graduation.warehouse.service.impl.exeption.FilterNotSupportedException;
import ua.graduation.warehouse.service.impl.validation.ItemValidation;
import ua.graduation.warehouse.service.impl.validation.ProductOwnerValidation;
import ua.graduation.warehouse.utils.FormatterDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Service
public class ItemServiceImpl implements ItemService {


    public final ItemRepository itemRepository;
    public final ItemValidation itemValidation;
    private final ProductOwnerValidation productOwnerValidation;

    public final int showTopOwner;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemValidation itemValidation,
                           @Value("${item.top.owner}") int showTopOwner,
                           ProductOwnerValidation productOwnerValidation
                           ) {
        this.itemRepository = itemRepository;
        this.itemValidation = itemValidation;
        this.showTopOwner = showTopOwner;
        this.productOwnerValidation = productOwnerValidation;
    }

    @Override
    @Transactional
    public void addItem(Item item) {
        productOwnerValidation.checkProductOwnerExist(item.getProductOwnerId());
        ItemEntity itemEntity = getFormItemEntity(item, TypeOperation.ADD);
        itemEntity.setCategories(
                item.getCategories() != null ? getCategoryEntity(item.getCategories()) : null
        );
        itemRepository.addItem(itemEntity);
    }

    @Override
    @Transactional
    public void addToCurrentItem(Item item) {
        List<ItemEntity> itemEntities = itemRepository.getItemBy(item.getItemId());
        itemValidation.checkItemsIsPresent(itemEntities, Collections.singletonList(item.getItemId()));

        ItemEntity itemEntityCurrent = itemEntities.iterator().next();

        //формируем сущность в таблицу операций (количество item  оставляем из запроса)
        item.setPrice(itemEntityCurrent.getPrice());
        OperationEntity operationEntity = getFormOperationEntity(item, TypeOperation.ADD);

        //формируем сущность для обновления количества элементов тек + из запроса
        item.setCount(itemEntityCurrent.getCount() + item.getCount());
        ItemEntity itemEntity = getFormItemEntity(item);

        //Устанавливаваем явно двунаправленную связь
        operationEntity.setItemEntity(itemEntity);
        itemEntity.setOperationEntities(Collections.singletonList(operationEntity));

        itemRepository.updateItem(itemEntity);
    }

    @Override
    @Transactional
    public void withdraw(List<Item> itemList) {
        List<Integer> itemIdRequest = itemList.stream().map(Item::getItemId).collect(Collectors.toList());
        List<ItemEntity> itemsEntityCurrent = itemRepository.getListItemBy(itemIdRequest);

        itemValidation.checkItemsIsPresent(itemsEntityCurrent, itemIdRequest);

        List<ItemEntity> changeItemEntity = new ArrayList<>();
        for (Item item: itemList) {
            for (ItemEntity itemCurrent:itemsEntityCurrent) {
                if (item.getItemId() == itemCurrent.getId()) {
                    itemValidation.checkCountItemForWithdraw(itemCurrent.getCount(), item.getCount(), itemCurrent.getId());
                    item.setCount(itemCurrent.getCount() - item.getCount());
                    changeItemEntity.add(getFormItemEntity(item, TypeOperation.WITHDRAW));
                    break;
                }
            }
        }

        itemRepository.updateItem(changeItemEntity);
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
                        .categories(item.getCategories().stream()
                                .map(el->Category.builder()
                                .categoryName(el.getCategoryName())
                                .id(el.getId()).build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public StatisticInfoResponse getStatisticInformationAboutAmountAndTotalCostItems(ItemStatisticInfo itemStatisticInfo) {

        itemValidation.checkExistTypeOperation(itemStatisticInfo.getTypeOperation());
        TypeOperation typeOperation = TypeOperation.valueOf(itemStatisticInfo.getTypeOperation().toUpperCase());

        FilterBetweenDate filterBetweenDate;
        List<OperationEntity> itemEntityList;

        if (itemStatisticInfo.getDateFrom() != null && itemStatisticInfo.getDateTo() != null) {
            filterBetweenDate = FilterBetweenDate.builder()
                    .dateStringFrom(FormatterDate.getDateMinFormatter(itemStatisticInfo.getDateFrom()))
                    .dateStringTo(FormatterDate.getDateMinFormatter(itemStatisticInfo.getDateFrom())).build();
            itemEntityList = itemRepository.getAllOperationByTypeAndDateOperation(typeOperation, filterBetweenDate);

        } else if (itemStatisticInfo.getPeriod() != null) {
            itemValidation.checkExistTypePeriod(itemStatisticInfo.getPeriod());
            filterBetweenDate = FilterDate.valueOf(itemStatisticInfo.getPeriod().toUpperCase()).getPeriod();
            itemEntityList = itemRepository.getAllOperationByTypeAndDateOperation(typeOperation, filterBetweenDate);
        } else {
            throw new FilterNotSupportedException("Filter not supported - you should set period or by date filter");
        }

        return getCountStatisticsOperation(itemEntityList);
    }

    @Override
    public List<StatisticInfoResponse> getTotalCostItemsTopProductOwner() {
        List<ItemEntity> itemEntities = itemRepository.getAllItems();

        Map<Integer, List<ItemEntity>> map = itemEntities.stream()
                .collect(groupingBy(el -> el.getProductOwnerEntity().getIdProductOwner()));

        List<StatisticInfoResponse> listStatistic = new ArrayList<>();
        for (Map.Entry<Integer, List<ItemEntity>> item : map.entrySet()) {
            ProductOwnerEntity productOwnerEntity = item.getValue().get(0).getProductOwnerEntity();
            ProductOwner productOwner = ProductOwner.builder()
                    .companyName(Optional.ofNullable(productOwnerEntity.getCompanyName()))
                    .firstName(productOwnerEntity.getFirstName())
                    .lastName(productOwnerEntity.getLastName())
                    .idProductOwner(productOwnerEntity.getIdProductOwner())
                    .build();

            StatisticInfoResponse statisticInfoResponse = getCountStatistics(item.getValue());
            statisticInfoResponse.setProductOwner(productOwner);
            listStatistic.add(statisticInfoResponse);
        }
        listStatistic.sort(Comparator.comparing(StatisticInfoResponse::getTotalCost, Comparator.reverseOrder()));
        return listStatistic.stream().limit(showTopOwner).collect(Collectors.toList());

    }

    private ItemEntity getFormItemEntity(Item item, TypeOperation typeOperation) {
        OperationEntity operationEntity = getFormOperationEntity(item, typeOperation);
        ItemEntity itemEntity = getFormItemEntity(item);

        operationEntity.setItemEntity(itemEntity);
        itemEntity.setOperationEntities(Collections.singletonList(operationEntity));

        return itemEntity;
    }

    private OperationEntity getFormOperationEntity(Item item, TypeOperation typeOperation) {
        LocalDateTime dateOperation =  LocalDateTime.now();
        return OperationEntity.builder()
                .price(item.getPrice())
                .count(item.getCount())
                .dateOperation(dateOperation)
                .typeOperationEntity(TypeOperationEntity.builder().id(typeOperation.getTypeId()).build())
                .build();
    }

    private ItemEntity getFormItemEntity(Item item) {
        return ItemEntity.builder()
                .productOwnerEntity(ProductOwnerEntity.builder().idProductOwner(item.getProductOwnerId()).build())
                .count(item.getCount())
                .id(item.getItemId())
                .dateAdd(LocalDateTime.now())
                .price(item.getPrice())
                .title(item.getTitle())
                .build();
    }

    private List<CategoryEntity> getCategoryEntity(List<Integer> categoryId) {
        return categoryId.stream().map(el->CategoryEntity.builder().id(el).build())
                .collect(Collectors.toList());
    }

    private StatisticInfoResponse getCountStatistics(List<ItemEntity> itemEntityList) {
        BigDecimal totalCost = BigDecimal.ZERO;
        int totalCount = 0;

        for (ItemEntity item : itemEntityList) {
            if (item.getCount() > 0 && item.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal costItem = item.getPrice().multiply(BigDecimal.valueOf(item.getCount()));
                totalCost = totalCost.add(costItem);
                totalCount += item.getCount();
            }
        }

        return StatisticInfoResponse.builder()
                .totalCount(totalCount)
                .totalCost(totalCost)
                .build();
    }

    private StatisticInfoResponse getCountStatisticsOperation(List<OperationEntity> operationEntities) {
        BigDecimal totalCost = BigDecimal.ZERO;
        int totalCount = 0;

        for (OperationEntity item : operationEntities) {
            if (item.getCount() > 0 && item.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal costItem = item.getPrice().multiply(BigDecimal.valueOf(item.getCount()));
                totalCost = totalCost.add(costItem);
                totalCount += item.getCount();
            }
        }
        return StatisticInfoResponse.builder()
                .totalCount(totalCount)
                .totalCost(totalCost)
                .build();
    }

}
