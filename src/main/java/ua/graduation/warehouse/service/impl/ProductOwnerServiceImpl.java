package ua.graduation.warehouse.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.ProductOwnerRepository;
import ua.graduation.warehouse.repository.model.EmailEntity;
import ua.graduation.warehouse.repository.model.PhoneEntity;
import ua.graduation.warehouse.repository.model.ProductOwnerEntity;
import ua.graduation.warehouse.repository.model.TypeContactEntity;
import ua.graduation.warehouse.service.catalog.TypeContact;
import ua.graduation.warehouse.service.entity.request.Contacts;
import ua.graduation.warehouse.service.entity.request.Email;
import ua.graduation.warehouse.service.entity.request.Phone;
import ua.graduation.warehouse.service.entity.request.ProductOwner;
import ua.graduation.warehouse.service.ProductOwnerService;
import ua.graduation.warehouse.service.impl.exeption.NotFoundOwner;
import ua.graduation.warehouse.service.impl.exeption.OwnerСanNotBeDeletedException;
import ua.graduation.warehouse.service.impl.validation.ProductOwnerValidation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOwnerServiceImpl implements ProductOwnerService {

    private final ProductOwnerRepository productOwnerRepository;
    private final ItemRepository itemRepository;
    private final ProductOwnerValidation productOwnerValidation;


    public ProductOwnerServiceImpl(ProductOwnerRepository productOwnerRepository,
                                   ItemRepository itemRepository,
                                   ProductOwnerValidation productOwnerValidation
                                   ) {
        this.productOwnerRepository = productOwnerRepository;
        this.itemRepository = itemRepository;
        this.productOwnerValidation = productOwnerValidation;
    }

    @Override
    @Transactional
    public int addProductOwner(ProductOwner productOwner) {
        Contacts contacts = productOwner.getContacts();
        List<EmailEntity> emailEntities = null;
        List<PhoneEntity> phoneEntities = null;

        productOwnerValidation.checkContactExist(productOwner.getContacts());

        ProductOwnerEntity productOwnerEntity =   ProductOwnerEntity.builder()
                .companyName(productOwner.getCompanyName().orElse(null))
                .firstName(productOwner.getFirstName())
                .lastName(productOwner.getLastName())
                .build();

        if (contacts.getEmails() != null)
            emailEntities = getEmailEntity(contacts.getEmails(), productOwnerEntity);
        if (contacts.getPhones() != null)
            phoneEntities = getPhoneEntity(contacts.getPhones(), productOwnerEntity);

        productOwnerEntity.setPhoneEntities(phoneEntities);
        productOwnerEntity.setEmailEntities(emailEntities);


        return productOwnerRepository.addProductOwner(productOwnerEntity);
    }

    @Override
    @Transactional
    public void deleteProductOwner(int idProductOwner) {
        if (!itemRepository.getOneEntityOwnersBy(idProductOwner).isEmpty()) {
            throw new OwnerСanNotBeDeletedException("owner can not be deleted, product owner has item");
        }

        if (productOwnerRepository.removeProductOwner(idProductOwner) == 0)
            throw new NotFoundOwner(String.format("not found owner id = %s", idProductOwner));
    }

    private List<EmailEntity> getEmailEntity(List<Email> emails, ProductOwnerEntity productOwnerEntity) {
        return  emails.stream()
                .map(email -> EmailEntity
                        .builder()
                        .email(email.getEmail())
                        .typeContactEntity(getTypeContactEntity(email.getTypeContact()))
                        .productOwnerEntity(productOwnerEntity)
                        .build())
                .collect(Collectors.toList());
    }

    private List<PhoneEntity> getPhoneEntity(List<Phone> phones, ProductOwnerEntity productOwnerEntity) {
        return  phones.stream()
                .map(p -> PhoneEntity
                        .builder()
                        .phone(p.getPhone())
                        .typeContactEntity(getTypeContactEntity(p.getTypeContact()))
                        .productOwnerEntity(productOwnerEntity)
                        .build())
                .collect(Collectors.toList());
    }

    private TypeContactEntity getTypeContactEntity(String type) {
        productOwnerValidation.checkExistTypeContact(type);
        return TypeContactEntity.builder().id(TypeContact.valueOf(type.toUpperCase()).getTypeId()).build();
    }

}
