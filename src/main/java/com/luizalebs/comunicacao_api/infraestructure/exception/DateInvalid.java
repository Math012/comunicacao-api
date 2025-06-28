package com.luizalebs.comunicacao_api.infraestructure.exception;

public class DateInvalid extends RuntimeException {
    public DateInvalid(String message) {
        super(message);
    }
}
