package ua.graduation.warehouse.repository;

import ua.graduation.warehouse.repository.model.ProductOwnerEntity;

public interface ProductOwnerRepository {
    int addProductOwner(ProductOwnerEntity productOwnerEntity);
    int removeProductOwner(int idProduct);
}
