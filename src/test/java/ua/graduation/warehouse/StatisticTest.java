package ua.graduation.warehouse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.model.ItemEntity;
import ua.graduation.warehouse.repository.model.OperationEntity;
import ua.graduation.warehouse.repository.model.ProductOwnerEntity;
import ua.graduation.warehouse.service.catalog.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterBetweenDate;
import ua.graduation.warehouse.service.entity.request.ItemStatisticInfo;
import ua.graduation.warehouse.service.entity.response.StatisticInfoResponse;
import ua.graduation.warehouse.service.impl.ItemServiceImpl;
import ua.graduation.warehouse.service.impl.validation.ItemValidation;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StatisticTest {


    @Mock
    private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    @Mock
    private ItemValidation itemValidation = Mockito.mock(ItemValidation.class);

    private int topOwnersCount = 5;
    private ItemServiceImpl itemService = new ItemServiceImpl(itemRepository, itemValidation, topOwnersCount);


    @Test
    @DisplayName("check total statistic when operation = 1")
    public void testCase1GetStatisticInformationAboutAmountAndTotalCostItem() {
        //Given
        List<OperationEntity> operationEntityList = Collections.singletonList(OperationEntity.builder()
                .price(new BigDecimal("100.54"))
                .count(10).build());

        ItemStatisticInfo itemStatisticInfo = getAnyItemStatisticInfo();

        when(itemRepository.getAllOperationByTypeAndDateOperation(
                any(TypeOperation.class),
                any(FilterBetweenDate.class)))
                .thenReturn(operationEntityList);

        StatisticInfoResponse expectedResult =
                StatisticInfoResponse.builder().totalCount(10).totalCost(new BigDecimal("1005.4")).build();


        //When
        StatisticInfoResponse infoResponse
                = itemService.getStatisticInformationAboutAmountAndTotalCostItems(itemStatisticInfo);

        //Then
        assertThat(infoResponse.getTotalCost()).isEqualByComparingTo(expectedResult.getTotalCost());
        assertThat(infoResponse.getTotalCount()).isEqualByComparingTo(expectedResult.getTotalCount());
    }


    @Test
    @DisplayName("check total statistic when operation > 1")
    public void testCase2GetStatisticInformationAboutAmountAndTotalCostItem() {
        //Given
        ItemStatisticInfo itemStatisticInfo = getAnyItemStatisticInfo();

        when(itemRepository.getAllOperationByTypeAndDateOperation(
                any(TypeOperation.class),
                any(FilterBetweenDate.class)))
                .thenReturn(getListOperation());

        StatisticInfoResponse expectedResult =
                StatisticInfoResponse.builder().totalCount(1031).totalCost(new BigDecimal("300501")).build();

        //When
        StatisticInfoResponse infoResponse
                = itemService.getStatisticInformationAboutAmountAndTotalCostItems(itemStatisticInfo);

        //Then
        assertThat(infoResponse.getTotalCount()).isEqualByComparingTo(expectedResult.getTotalCount());
        assertThat(infoResponse.getTotalCost()).isEqualByComparingTo(expectedResult.getTotalCost());
    }

    @Test
    @DisplayName("check statistic operation when operation == 0")
    public void testCase3GetStatisticInformationAboutAmountAndTotalCostItems() {
        //Given
        ItemStatisticInfo itemStatisticInfo = getAnyItemStatisticInfo();

        when(itemRepository.getAllOperationByTypeAndDateOperation(
                any(TypeOperation.class),
                any(FilterBetweenDate.class)))
                .thenReturn(Collections.emptyList());

        StatisticInfoResponse expectedResult =
                StatisticInfoResponse.builder().totalCount(0).totalCost(BigDecimal.ZERO).build();

        //When
        StatisticInfoResponse infoResponse
                = itemService.getStatisticInformationAboutAmountAndTotalCostItems(itemStatisticInfo);

        //Then
        assertThat(infoResponse.getTotalCount()).isEqualByComparingTo(expectedResult.getTotalCount());
        assertThat(infoResponse.getTotalCost()).isEqualByComparingTo(expectedResult.getTotalCost());
    }

    @Test
    @DisplayName("check statistic top 5 owners when owners > 5")
    public void testCase4TetTotalCostItemsTopProductOwner() {
        //Given
        when(itemRepository.getAllItems()).thenReturn(getListItemProductOwner());


        //When
         List<StatisticInfoResponse> infoResponse
                = itemService.getTotalCostItemsTopProductOwner();

        //Then
        assertThat(infoResponse.size()).describedAs(String.format("should be size %s", topOwnersCount))
                .isEqualTo(topOwnersCount);

    }

    @Test
    @DisplayName("check statistic sort descending")
    public void testCase5TetTotalCostItemsTopProductOwner() {
        //Given
        when(itemRepository.getAllItems()).thenReturn(getListItemProductOwner());


        //When
        List<StatisticInfoResponse> infoResponse
                = itemService.getTotalCostItemsTopProductOwner();

        //Then
        assertThat(infoResponse).extracting(StatisticInfoResponse::getTotalCost)
                .describedAs("should be sort by descending")
                .isSortedAccordingTo(Comparator.reverseOrder());

    }

    @Test
    @DisplayName("check calculation is done correctly")
    public void testCase6TetTotalCostItemsTopProductOwner() {
        //Given
        when(itemRepository.getAllItems()).thenReturn(getListItemProductOwner());

        //When
        List<StatisticInfoResponse> infoResponse
                = itemService.getTotalCostItemsTopProductOwner();

        //Then

        infoResponse.stream().forEach(el-> System.out.println(el));

    }


    public ItemStatisticInfo getAnyItemStatisticInfo() {
        return ItemStatisticInfo.builder()
                .period("CURRENT_DAY")
                .typeOperation("WITHDRAW")
                .build();
    }

    private List<OperationEntity> getListOperation() {

        OperationEntity operationEntity1 =
                OperationEntity.builder().price(new BigDecimal("0.5")).count(1000).build();
        OperationEntity operationEntity2 =
                OperationEntity.builder().price(new BigDecimal("1")).count(1).build();
        OperationEntity operationEntity3 =
                OperationEntity.builder().price(new BigDecimal("10000")).count(30).build();

        return asList(operationEntity1, operationEntity2, operationEntity3);
    }

    private List<ItemEntity> getListItemProductOwner() {

        ArrayList<ProductOwnerEntity>  productOwnerEntities = getListProductOwner(6);

        ArrayList<ItemEntity> itemEntities = new ArrayList<>();

        // (0,75   * 15 ) + (5000 * 1) = 5011.25
        //У клиента 2
        itemEntities.add(getItemEntity(new BigDecimal("0.75"), 15, productOwnerEntities.get(0)));
        itemEntities.add(getItemEntity(new BigDecimal("1"), 5000, productOwnerEntities.get(0)));

        //Исключаем (количество элементов 0)
        // (15 * 1000) = 15000
        itemEntities.add(getItemEntity(new BigDecimal("15"), 1000, productOwnerEntities.get(1)));
        itemEntities.add(getItemEntity(new BigDecimal("1000"), 0, productOwnerEntities.get(1)));

        // (99999.99 * 9) = 899999.91
        itemEntities.add(getItemEntity(new BigDecimal("99999.99"), 9, productOwnerEntities.get(2)));

        //455.5
        itemEntities.add(getItemEntity(new BigDecimal("45.55"), 10, productOwnerEntities.get(3)));

        //455.5
        itemEntities.add(getItemEntity(new BigDecimal("45.55"), 5, productOwnerEntities.get(4)));
        itemEntities.add(getItemEntity(new BigDecimal("45.55"), 5, productOwnerEntities.get(4)));

        //9
        itemEntities.add(getItemEntity(new BigDecimal("3"), 3, productOwnerEntities.get(5)));


        return itemEntities;
    }


    private ItemEntity getItemEntity(BigDecimal price, int count, ProductOwnerEntity productOwnerEntity) {
        return ItemEntity.builder()
                .count(count)
                .price(price)
                .productOwnerEntity(productOwnerEntity)
                .build();
    }



    public ArrayList<ProductOwnerEntity> getListProductOwner (int countOwnrs) {
        ArrayList<ProductOwnerEntity> productOwnerEntities = new ArrayList();

        for (int i = 1; i <= countOwnrs; i++) {
            ProductOwnerEntity productOwner = ProductOwnerEntity.builder()
                    .idProductOwner(i)
                    .lastName("lastName " + i)
                    .firstName("firstName " + i)
                    .companyName("test")
                    .build();

            productOwnerEntities.add(productOwner);
        }

        return productOwnerEntities;

    }
}
