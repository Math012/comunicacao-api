package com.luizalebs.comunicacao_api.business.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;

import java.time.LocalDateTime;

public class ComunicacaoOutDTOFixture {
    public static ComunicacaoOutDTO build(Long id,
    LocalDateTime dataHoraEnvio,
    String nomeDestinatario,
    String emailDestinatario,
    String telefoneDestinatario,
    String mensagem,
    String nomeRemetente,
    ModoEnvioEnum modoDeEnvio,
    StatusEnvioEnum statusEnvio){
        return new ComunicacaoOutDTO(id,dataHoraEnvio,nomeDestinatario,emailDestinatario,telefoneDestinatario,mensagem,nomeRemetente,modoDeEnvio,statusEnvio);

    }
}
