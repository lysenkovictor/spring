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
import ua.graduation.warehouse.service.entity.request.ProductOwner;
import ua.graduation.warehouse.service.entity.response.StatisticInfoResponse;
import ua.graduation.warehouse.service.impl.ItemServiceImpl;
import ua.graduation.warehouse.service.impl.validation.CategoryValidation;
import ua.graduation.warehouse.service.impl.validation.ItemValidation;
import ua.graduation.warehouse.service.impl.validation.ProductOwnerValidation;

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

    @Mock
    CategoryValidation categoryValidation = Mockito.mock(CategoryValidation.class);

    @Mock
    private ProductOwnerValidation productOwnerValidation = Mockito.mock(ProductOwnerValidation.class);


    private int topOwnersCount = 5;
    private ItemServiceImpl itemService = new ItemServiceImpl(itemRepository, itemValidation,
            topOwnersCount, productOwnerValidation, categoryValidation);


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

        assertThat(infoResponse).isEqualTo(getListStatisticInfoExpectedResult());
    }

    public ItemStatisticInfo getAnyItemStatisticInfo() {
      return ItemStatisticInfo.builder()
                .period("CURRENT_DAY")
                .typeOperation("WITHDRAW")
                .build();

    }

    public  List<StatisticInfoResponse> getListStatisticInfoExpectedResult() {
        ArrayList<ProductOwnerEntity>  productOwnerEntities = getListProductOwner(6);

         StatisticInfoResponse statisticInfoResponse = StatisticInfoResponse.builder()
                .totalCost(new BigDecimal("899999.91"))
                .totalCount(9)
                .productOwner(getProductOwnerResponse(productOwnerEntities.get(2)))
                .build();

        StatisticInfoResponse statisticInfoResponse2 = StatisticInfoResponse.builder()
                .totalCost(new BigDecimal("15000"))
                .totalCount(1000)
                .productOwner(getProductOwnerResponse(productOwnerEntities.get(1)))
                .build();

        StatisticInfoResponse statisticInfoResponse3 = StatisticInfoResponse.builder()
                .totalCost(new BigDecimal("5001.5"))
                .totalCount(5015)
                .productOwner(getProductOwnerResponse(productOwnerEntities.get(0)))
                .build();

        BigDecimal totalCost = new BigDecimal("455.50");
        int totalCount = 10;

        StatisticInfoResponse statisticInfoResponse4 = StatisticInfoResponse.builder()
                .totalCost(totalCost)
                .totalCount(totalCount)
                .productOwner(getProductOwnerResponse(productOwnerEntities.get(3)))
                .build();

        StatisticInfoResponse statisticInfoResponse5 = StatisticInfoResponse.builder()
                .totalCost(totalCost)
                .totalCount(totalCount)
                .productOwner(getProductOwnerResponse(productOwnerEntities.get(4)))
                .build();

        return asList(statisticInfoResponse, statisticInfoResponse2, statisticInfoResponse3
        ,statisticInfoResponse4,statisticInfoResponse5);
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
        //У клиента > item
        itemEntities.add(getItemEntity(new BigDecimal("0.1"), 15, productOwnerEntities.get(0)));
        itemEntities.add(getItemEntity(new BigDecimal("1"), 5000, productOwnerEntities.get(0)));

        //У клиента есть item > price 0
        // (15 * 1000) = 15000
        itemEntities.add(getItemEntity(new BigDecimal("15"), 1000, productOwnerEntities.get(1)));
        itemEntities.add(getItemEntity(new BigDecimal("1000"), 0, productOwnerEntities.get(1)));

        //(899999.91)
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
                    .companyName(null)
                    .build();

            productOwnerEntities.add(productOwner);
        }

        return productOwnerEntities;
    }

    public ProductOwner getProductOwnerResponse(ProductOwnerEntity productOwnerEntity) {
       return ProductOwner.builder()
                .firstName(productOwnerEntity.getFirstName())
                .lastName(productOwnerEntity.getLastName())
                .idProductOwner(productOwnerEntity.getIdProductOwner())
               .companyName(Optional.empty())
                .build();
    }

}
