package com.luizalebs.comunicacao_api.business.requests;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;

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

    public static ComunicacaoInDTO buildController(LocalDateTime dataHoraEnvio,
                                         String nomeDestinatario,
                                         String emailDestinatario,
                                         String telefoneDestinatario,
                                         String mensagem,
                                         String nomeRemetente,
                                         ModoEnvioEnum modoDeEnvio,
                                         StatusEnvioEnum statusEnvio) {
        return new ComunicacaoInDTO(dataHoraEnvio,nomeDestinatario,emailDestinatario,telefoneDestinatario,mensagem,nomeRemetente,modoDeEnvio, statusEnvio);
    }
}