package com.luizalebs.comunicacao_api.infraestructure.exception;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
