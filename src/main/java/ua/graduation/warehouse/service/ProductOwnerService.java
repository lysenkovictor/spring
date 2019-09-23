package ua.graduation.warehouse.service;

import ua.graduation.warehouse.service.entity.request.ProductOwner;

public interface ProductOwnerService {

    int addProductOwner(ProductOwner productOwner);
    void deleteProductOwner(int idProductOwner);

}
