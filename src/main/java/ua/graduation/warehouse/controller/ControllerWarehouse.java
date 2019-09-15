package ua.graduation.warehouse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.graduation.warehouse.service.ProductOwnerService;
import ua.graduation.warehouse.service.entity.request.ProductOwner;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "owner")
public class ControllerWarehouse {

    private final ProductOwnerService productOwnerService;
    private final Logger logger = LoggerFactory.getLogger(ControllerWarehouse.class);


    public ControllerWarehouse(ProductOwnerService productOwnerService) {
        this.productOwnerService = productOwnerService;
    }

    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProductOwner(@Valid @RequestBody ProductOwner productOwner) {

        int productOwnerId = productOwnerService.addProductOwner(productOwner);

        productOwner.setIdProductOwner(productOwnerId);
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(productOwner);

        return responseEntity;
    }

    @DeleteMapping(path = "delete/{idProductOwner}")
    public int deleteProductOwner(@PathVariable int idProductOwner) {
        return productOwnerService.deleteProductOwner(idProductOwner);
    }

}
