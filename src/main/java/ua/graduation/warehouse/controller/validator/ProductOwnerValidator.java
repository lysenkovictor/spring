package ua.graduation.warehouse.controller.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.graduation.warehouse.repository.model.ProductOwnerEntity;

@Service
public class ProductOwnerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductOwnerEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductOwnerEntity productOwnerEntity = (ProductOwnerEntity) target;
        if (productOwnerEntity.getFirstName() == null) {
            errors.rejectValue("first_name", "хуй");
        }
    }
}
