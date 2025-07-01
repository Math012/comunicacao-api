package com.luizalebs.comunicacao_api.business.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ComunicacaoInDTOFixture {
    public static ComunicacaoInDTO build(LocalDateTime dataHoraEnvio,
                                        String nomeDestinatario,
                                        String emailDestinatario,
                                        String telefoneDestinatario,
                                        String mensagem,
                                        String nomeRemetente,
                                        ModoEnvioEnum modoDeEnvio) {
        return new ComunicacaoInDTO(dataHoraEnvio,nomeDestinatario,emailDestinatario,telefoneDestinatario,mensagem,nomeRemetente,modoDeEnvio, StatusEnvioEnum.PENDENTE);
    }
}
