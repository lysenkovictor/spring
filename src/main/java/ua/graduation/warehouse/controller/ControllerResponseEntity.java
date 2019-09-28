package ua.graduation.warehouse.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

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

    public ResponseEntity getResponseEntityStatusHttpStatusError(BindingResult result) {
        StringBuilder stringBuilder = new StringBuilder();
           result.getFieldErrors().forEach(
                    (el->stringBuilder
                            .append("{")
                            .append("\"")
                            .append("error")
                            .append("\"")
                            .append(":")
                            .append("\"")
                            .append(el.getField())
                            .append("=")
                            .append(el.getDefaultMessage())
                            .append("\"")
                            .append("}")));

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(stringBuilder);
    }
}
