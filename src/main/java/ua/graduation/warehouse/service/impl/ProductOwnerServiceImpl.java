package ua.graduation.warehouse.service.impl;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.ItemRepository;
import ua.graduation.warehouse.repository.ProductOwnerRepository;
import ua.graduation.warehouse.repository.model.EmailEntity;
import ua.graduation.warehouse.repository.model.PhoneEntity;
import ua.graduation.warehouse.repository.model.ProductOwnerEntity;
import ua.graduation.warehouse.repository.model.TypeContactEntity;
import ua.graduation.warehouse.service.entity.Contacts;
import ua.graduation.warehouse.service.entity.Email;
import ua.graduation.warehouse.service.entity.Phone;
import ua.graduation.warehouse.service.entity.ProductOwner;
import ua.graduation.warehouse.service.ProductOwnerService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductOwnerServiceImpl implements ProductOwnerService {

    private final ProductOwnerRepository productOwnerRepository;
    private final ItemRepository itemRepository;


    public ProductOwnerServiceImpl(ProductOwnerRepository productOwnerRepository,
                                   ItemRepository itemRepository
                                   ) {
        this.productOwnerRepository = productOwnerRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public int addProductOwner(ProductOwner productOwner) {

        Contacts contacts = productOwner.getContacts();

        List<EmailEntity> emailEntities = null;
        Set<PhoneEntity> phoneEntities = null;


        if (contacts.getEmails() != null)
            emailEntities = getEmailEntity(contacts.getEmails());
        if (contacts.getPhones() != null)
            phoneEntities = getPhoneEntity(contacts.getPhones());

                ProductOwnerEntity productOwnerEntity =   ProductOwnerEntity.builder()
                .companyName(productOwner.getCompanyName().orElse(null))
                .firstName(productOwner.getFirstName())
                .lastName(productOwner.getLastName())
                .phoneEntities(phoneEntities)
                .emailEntities(emailEntities)
                .build();

       return productOwnerRepository.addProductOwner(productOwnerEntity);

    }

    @Override
    public int deleteProductOwner(int idProductOwner) {

        if (!itemRepository.getAllOwnerItemsBy(idProductOwner).isEmpty()) {
            throw new RuntimeException("owner can not be deleted");
        }
        return productOwnerRepository.removeProductOwner(idProductOwner);
    }


    private List<EmailEntity> getEmailEntity(List<Email> emails) {

        return  emails.stream()
                .map(email -> EmailEntity
                        .builder()
                        .email(email.getEmail())
                        .typeContactEntity(TypeContactEntity.builder().id(email.getTypeId()).build())
                        .build())
                .collect(Collectors.toList());
    }

    private Set<PhoneEntity> getPhoneEntity(List<Phone> phones) {

        return  phones.stream()
                .peek(el-> System.out.println(el.typeId))
                .map(p -> PhoneEntity
                        .builder()
                        .phone(p.getPhone())
                        .typeContactEntity(TypeContactEntity.builder().id(p.getTypeId()).build())
                        .build())
                .collect(Collectors.toSet());
    }


}