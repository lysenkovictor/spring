package ua.graduation.warehouse.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.graduation.warehouse.service.CategoryService;
import ua.graduation.warehouse.service.entity.request.Category;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "category")
public class ControllerCategory {

    private final CategoryService categoryService;
    private final ControllerResponseEntity controllerResponseEntity;

    public ControllerCategory(CategoryService categoryService,
                              ControllerResponseEntity controllerResponseEntity) {
        this.controllerResponseEntity = controllerResponseEntity;
        this.categoryService = categoryService;
    }


    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProductOwner(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
        return controllerResponseEntity.getResponseEntityStatusHttpStatusOk();
    }
}
