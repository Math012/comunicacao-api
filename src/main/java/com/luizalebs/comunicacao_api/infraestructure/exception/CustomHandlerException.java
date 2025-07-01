package com.luizalebs.comunicacao_api.infraestructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    // 404
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<StructException> handlerResourceNotFound(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DateInvalid.class)
    public ResponseEntity<StructException> handlerDateInvalid(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<StructException> handlerInvalidFieldsException(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }


}
