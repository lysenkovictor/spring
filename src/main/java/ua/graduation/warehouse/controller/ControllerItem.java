package ua.graduation.warehouse.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.graduation.warehouse.service.ItemService;
import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.entity.request.ItemStatisticInfo;
import ua.graduation.warehouse.service.entity.request.Items;
import ua.graduation.warehouse.service.entity.response.ItemResponse;
import ua.graduation.warehouse.service.entity.response.StatisticInfoResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "item")
public class ControllerItem {

    private final ItemService itemService;
    private final ControllerResponseEntity controllerResponseEntity;

    public ControllerItem(ItemService itemService, ControllerResponseEntity controllerResponseEntity) {
        this.itemService = itemService;
        this.controllerResponseEntity = controllerResponseEntity;
    }

    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public ResponseEntity createItem(@Valid @RequestBody Item item) {
        itemService.addItem(item);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk();
    }


    @RequestMapping(path = "update")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateItem(@Valid @RequestBody Item item) {
        itemService.addToCurrentItem(item);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk();
    }

    @RequestMapping(path = "getByOwner/{idProductOwner}")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getAllItemsOwnerBy(@Valid @PathVariable int idProductOwner) {
        List<ItemResponse>  itemResponses = itemService.getAllItemsOwnerBy(idProductOwner);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk(itemResponses);
    }


    @RequestMapping(path = "totalStatistic")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getStatisticItem(@Valid @RequestBody ItemStatisticInfo item) {
        StatisticInfoResponse itemResponses = itemService.getStatisticInformationAboutAmountAndTotalCostItems(item);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk(itemResponses);
    }


    @RequestMapping(path = "withdraw")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateItem(@Valid @RequestBody Items itemList) {
        itemService.withdraw(itemList.getItemList());
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk();
    }


    @RequestMapping(path = "topStatistic")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getStatisticItem() {
        List<StatisticInfoResponse> itemResponses = itemService.getTotalCostItemsTopProductOwner();
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk(itemResponses);
    }


}
