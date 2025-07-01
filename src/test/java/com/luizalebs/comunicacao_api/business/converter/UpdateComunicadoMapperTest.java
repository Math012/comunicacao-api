package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.response.ComunicacaoOutDTOFixture;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class UpdateComunicadoMapperTest {

    UpdateComunicadoMapper updateComunicadoMapper;
    ComunicacaoOutDTO comunicacaoOutDTO;
    ComunicacaoEntity comunicacaoEntity;
    ComunicacaoEntity comunicacaoEntityModificado;
    LocalDateTime dataHora;
    ModoEnvioEnum modoEnvioEnum;
    StatusEnvioEnum statusEnvioEnum;

    @BeforeEach
    void setup(){
        dataHora = LocalDateTime.of(2025,10,15,14,12,15);
        updateComunicadoMapper = Mappers.getMapper(UpdateComunicadoMapper.class);
        comunicacaoEntity = ComunicacaoEntity.builder()
                .id(null)
                .dataHoraEnvio(dataHora)
                .nomeDestinatario("teste")
                .emailDestinatario("teste@teste.com")
                .telefoneDestinatario("111111111")
                .mensagem("mensagem teste")
                .nomeRemetente("teste teste")
                .modoDeEnvio(modoEnvioEnum.EMAIL)
                .statusEnvio(statusEnvioEnum.PENDENTE)
                .build();

        comunicacaoOutDTO = ComunicacaoOutDTOFixture.build(
                null,
                dataHora,
                null,
                "alterado@teste.com",
                "111111119",
                null,
                null,
                null,
                statusEnvioEnum.ENVIADO
        );

        comunicacaoEntityModificado = ComunicacaoEntity.builder()
                .id(null)
                .dataHoraEnvio(dataHora)
                .nomeDestinatario("teste")
                .emailDestinatario("alterado@teste.com")
                .telefoneDestinatario("111111119")
                .mensagem("mensagem teste")
                .nomeRemetente("teste teste")
                .modoDeEnvio(modoEnvioEnum.EMAIL)
                .statusEnvio(statusEnvioEnum.ENVIADO)
                .build();
    }

    @Test
    void deveFazerUpdateComunicadoEntity(){
        ComunicacaoEntity response = updateComunicadoMapper.updateComunicado(comunicacaoOutDTO,comunicacaoEntity);
        assertEquals(comunicacaoEntityModificado,response);
    }
}
