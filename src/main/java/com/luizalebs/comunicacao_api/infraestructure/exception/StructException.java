package com.luizalebs.comunicacao_api.infraestructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StructException {
    private Date timestamp;
    private String msg;
    private String detail;
}
