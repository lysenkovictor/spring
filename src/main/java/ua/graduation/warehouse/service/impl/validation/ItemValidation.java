package ua.graduation.warehouse.service.impl.validation;

import org.springframework.stereotype.Service;
import ua.graduation.warehouse.repository.model.ItemEntity;
import ua.graduation.warehouse.service.catalog.TypeOperation;
import ua.graduation.warehouse.service.entity.date.FilterDate;
import ua.graduation.warehouse.service.entity.request.Item;
import ua.graduation.warehouse.service.impl.exeption.ItemNotFoundException;
import ua.graduation.warehouse.service.impl.exeption.NotFoundTypeOperationException;
import ua.graduation.warehouse.service.impl.exeption.WithdrawalAmountExceededException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemValidation {

    public void checkCountItemForWithdraw(int currentCount, int withdrawCount, int idItem) {
        if (currentCount < withdrawCount) {
            throw new WithdrawalAmountExceededException(
                    String.format("%s - withdrawal totalCount exceeded, currentCount = %s, withdrawCount = %s",
                            idItem, currentCount, withdrawCount));
        }
    }

    public void checkItemsIsPresent(List<ItemEntity> itemEntityList, List<Integer> itemList) {
        ArrayList<Integer> idNotFound = new ArrayList();

        List<Integer> listIdCurrent = itemEntityList.stream().map(ItemEntity::getId).collect(Collectors.toList());
        for (int id : itemList) {
            if (!listIdCurrent.contains(id)) {
                idNotFound.add(id);
            }
        }
        if (!idNotFound.isEmpty()) {
            throw new ItemNotFoundException(String.format("%s - item not found", idNotFound));
        }
    }

    public void checkExistTypeOperation(String type) {
        try {
            TypeOperation.valueOf(type.toUpperCase());
        }catch (RuntimeException ex) {
            throw  new NotFoundTypeOperationException("available types contact: " + Arrays.toString(TypeOperation.values()));
        }
    }

    public void checkExistTypePeriod(String type) {
        try {
            FilterDate.valueOf(type.toUpperCase());
        }catch (RuntimeException ex) {
            throw  new NotFoundTypeOperationException("available types contact: " + Arrays.toString(FilterDate.values()));
        }
    }

    public void setItemPositive(Item item) {
        item.setCount(Math.abs(item.getCount()));
    }

}
