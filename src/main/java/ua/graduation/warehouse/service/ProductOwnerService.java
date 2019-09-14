package ua.graduation.warehouse.service;

import ua.graduation.warehouse.service.entity.ProductOwner;

public interface ProductOwnerService {

    int addProductOwner(ProductOwner productOwner);
    int deleteProductOwner(int idProductOwner);

}
