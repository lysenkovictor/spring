package ua.graduation.warehouse.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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


    public ControllerWarehouse(ProductOwnerService productOwnerService,
                               ProductOwnerValidator productOwnerValidator,
                               ControllerResponseEntity controllerResponseEntity
                               ) {
        this.productOwnerService = productOwnerService;
        this.productOwnerValidator = productOwnerValidator;
        this.controllerResponseEntity = controllerResponseEntity;
    }

    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProductOwner(@Valid @RequestBody ProductOwner productOwner, BindingResult result) {
        productOwnerValidator.validate(productOwner, result);
        if (result.hasErrors() ) {
            return controllerResponseEntity.getResponseEntityStatusHttpStatusError(result);
        }
        int productOwnerId = productOwnerService.addProductOwner(productOwner);
        productOwner.setIdProductOwner(productOwnerId);

        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk(productOwner);
    }

    @DeleteMapping(path = "delete/{idProductOwner}")
    public ResponseEntity deleteProductOwner(@PathVariable int idProductOwner) {
        productOwnerService.deleteProductOwner(idProductOwner);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk();
    }


}
