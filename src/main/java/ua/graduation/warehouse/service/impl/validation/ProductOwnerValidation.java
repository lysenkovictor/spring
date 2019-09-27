package ua.graduation.warehouse.service.impl.validation;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.ProductOwnerRepository;
import ua.graduation.warehouse.service.catalog.TypeContact;
import ua.graduation.warehouse.service.entity.request.Contacts;
import ua.graduation.warehouse.service.impl.exeption.ContactNotExistException;
import ua.graduation.warehouse.service.impl.exeption.NotFoundOwner;
import ua.graduation.warehouse.service.impl.exeption.NotFoundTypeContactException;


import java.util.Arrays;

@Service
public class ProductOwnerValidation {

    private final ProductOwnerRepository productOwnerRepository;

    public ProductOwnerValidation(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;

    }

    public void checkContactExist(Contacts contacts) {
        if (contacts.getEmails() == null && contacts.getPhones() == null) {
            throw new ContactNotExistException("Client should have but at least one contact");
        }
    }

    public void checkExistTypeContact(String type) {
        try {
            TypeContact.valueOf(type.toUpperCase());
        }catch (RuntimeException ex) {
            throw  new NotFoundTypeContactException("available types contact: " + Arrays.toString(TypeContact.values()));
        }
    }

    public void checkProductOwnerExist(int productOwnerId) {
        if (productOwnerRepository.getSingletonListProductOwner(productOwnerId).isEmpty())
            throw  new NotFoundOwner("you should be add productOwner before add item");
    }
}
