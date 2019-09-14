package ua.graduation.warehouse.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.graduation.warehouse.service.ItemService;
import ua.graduation.warehouse.service.entity.Item;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "item")
public class ControllerItem {

    private ItemService itemService;

    public ControllerItem(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createItem(@Valid @RequestBody Item item) {
        itemService.addItem(item);
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body("succsesfiull");

        return responseEntity;
    }



    @RequestMapping(path = "update")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateItem(@Valid @RequestBody Item item) {
        itemService.updateItem(item);
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body("succsesfiull");

        return responseEntity;
    }




}
