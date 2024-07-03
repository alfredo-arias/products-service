package com.actividadgrupal.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ProductAlreadyExistsException  extends RuntimeException{
    private String message;
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
