package ua.graduation.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.graduation.warehouse.service.impl.exeption.WithdrawalAmountExceededException;

public class ServiceErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({WithdrawalAmountExceededException.class})
    public void handle(WithdrawalAmountExceededException e) {

    }
}
