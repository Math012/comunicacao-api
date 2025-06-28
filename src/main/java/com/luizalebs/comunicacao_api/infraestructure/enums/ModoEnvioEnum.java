package com.luizalebs.comunicacao_api.infraestructure.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ModoEnvioEnum {
    EMAIL,
    SMS,
    PUSH,
    WHATSAPP;
}
