package ua.graduation.warehouse.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.graduation.warehouse.service.CategoryService;
import ua.graduation.warehouse.service.entity.Category;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "category")
public class ControllerCategory {

    private final CategoryService categoryService;

    public ControllerCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @RequestMapping(path = "add")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createProductOwner(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);

        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body("succsesfiull");

        return responseEntity;
    }
}
