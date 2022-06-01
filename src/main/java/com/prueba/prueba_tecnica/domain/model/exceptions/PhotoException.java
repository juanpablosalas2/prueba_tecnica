package com.prueba.prueba_tecnica.domain.model.exceptions;

public class PhotoException  extends RuntimeException{

    public PhotoException(String message) {
        super(message);
    }

    public PhotoException(String message, Throwable cause) {
        super(message, cause);
    }
}