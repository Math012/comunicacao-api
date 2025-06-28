package com.luizalebs.comunicacao_api.infraestructure.exception;

public class UnexpectedErrorClient extends RuntimeException {
    public UnexpectedErrorClient(String message) {
        super(message);
    }
}
