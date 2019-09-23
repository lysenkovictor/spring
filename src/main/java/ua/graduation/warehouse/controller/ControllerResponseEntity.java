package ua.graduation.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerResponseEntity<T> {
    public ResponseEntity getResponseEntityStatusHttpStatusOk(T body) {
       return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    public ResponseEntity getResponseEntityStatusHttpStatusOk() {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"response\": 0}");
    }
}
