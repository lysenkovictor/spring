package ua.graduation.warehouse.service.impl.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundOwner extends RuntimeException {

    public NotFoundOwner(String message) {
        super(message);
    }
}
