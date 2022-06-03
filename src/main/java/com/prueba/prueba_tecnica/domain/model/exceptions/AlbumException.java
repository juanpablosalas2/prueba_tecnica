package com.prueba.prueba_tecnica.domain.model.exceptions;

public class AlbumException extends RuntimeException{

    public AlbumException(String message) {
        super(message);
    }

    public AlbumException(String message, Throwable cause) {
        super(message, cause);
    }
}