package ua.graduation.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.graduation.warehouse.service.ItemService;
import ua.graduation.warehouse.service.ProductOwnerService;
import ua.graduation.warehouse.service.entity.request.ProductOwner;
import ua.graduation.warehouse.controller.validator.ProductOwnerValidator;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "owner")
public class ControllerWarehouse {

    private final ProductOwnerService productOwnerService;
    private final ProductOwnerValidator productOwnerValidator;
    private final ControllerResponseEntity controllerResponseEntity;
    private final ItemService itemService;

    public ControllerWarehouse(ProductOwnerService productOwnerService,
                               ProductOwnerValidator productOwnerValidator,
                               ItemService itemService,
                               ControllerResponseEntity controllerResponseEntity
                               ) {
        this.productOwnerService = productOwnerService;
        this.productOwnerValidator = productOwnerValidator;
        this.itemService = itemService;
        this.controllerResponseEntity = controllerResponseEntity;
    }

    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProductOwner(@Valid @RequestBody ProductOwner productOwner, BindingResult result) {

        productOwnerValidator.validate(productOwner, result);
        StringBuilder stringBuilder = new StringBuilder();

        if (result.hasErrors() ) {
            result.getFieldErrors().forEach(
                    (el->stringBuilder
                            .append("{")
                            .append("\"")
                            .append("error")
                            .append("\"")
                            .append(":")
                            .append("\"")
                            .append(el.getField())
                            .append("=")
                            .append(el.getDefaultMessage())
                            .append("\"")
                            .append("}")));

            return ResponseEntity.status(400).body(stringBuilder.toString());
        }
        int productOwnerId = productOwnerService.addProductOwner(productOwner);

        productOwner.setIdProductOwner(productOwnerId);
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(productOwner);

        return responseEntity;
    }

    @DeleteMapping(path = "delete/{idProductOwner}")
    public ResponseEntity deleteProductOwner(@PathVariable int idProductOwner) {
        productOwnerService.deleteProductOwner(idProductOwner);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk();
    }


}
