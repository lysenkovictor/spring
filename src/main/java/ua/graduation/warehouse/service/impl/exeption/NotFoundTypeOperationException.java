package ua.graduation.warehouse.service.impl.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundTypeOperationException extends RuntimeException{
    public NotFoundTypeOperationException(String message) {
        super(message);
    }
}
