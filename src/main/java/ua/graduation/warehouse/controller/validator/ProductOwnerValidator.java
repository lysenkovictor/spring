package ua.graduation.warehouse.controller.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.graduation.warehouse.service.entity.request.ProductOwner;

@Service
public class ProductOwnerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductOwner.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductOwner productOwner = (ProductOwner) target;
        setErrorsValidator("firstName", productOwner.getFirstName(), errors);
        setErrorsValidator("lastName", productOwner.getLastName(), errors);

        if (productOwner.getContacts() == null) {
            String contacts = "contacts";
            errors.rejectValue(contacts, "", getMassage(contacts));
        }
    }

    private void setErrorsValidator(String nameField, String value, Errors errors) {
        if (value == null) {
            String errorMessage = String.format("%s can't be null", nameField);
            errors.rejectValue(nameField, "", errorMessage);
        }
    }

    public String getMassage(String nameField) {
        return String.format("%s can't be null", nameField);
    }

}
