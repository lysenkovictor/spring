package ua.graduation.warehouse.repository;

import ua.graduation.warehouse.repository.model.ProductOwnerEntity;

import java.util.List;

public interface ProductOwnerRepository {
    int addProductOwner(ProductOwnerEntity productOwnerEntity);
    int removeProductOwner(int idOwner);
    List<ProductOwnerEntity> getSingletonListProductOwner(int idOwner);
}
