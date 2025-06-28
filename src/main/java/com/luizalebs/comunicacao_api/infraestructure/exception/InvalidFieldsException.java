package com.luizalebs.comunicacao_api.infraestructure.exception;

public class InvalidFieldsException extends RuntimeException {
    public InvalidFieldsException(String message) {
        super(message);
    }
}
