package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.requests.ComunicacaoInDTOFixture;
import com.luizalebs.comunicacao_api.business.response.ComunicacaoOutDTOFixture;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ComunicadoMapperTest {

    ComunicadoMapper comunicadoMapper;

    ComunicacaoEntity comunicacaoEntity;

    ComunicacaoOutDTO comunicacaoOutDTO;

    ComunicacaoInDTO comunicacaoInDTO;

    List<ComunicacaoOutDTO> listComunicacaoOutDTO;

    List<ComunicacaoEntity> listComunicacaoEntity;

    LocalDateTime dataHora;

    ModoEnvioEnum modoEnvioEnum;

    StatusEnvioEnum statusEnvioEnum;

    @BeforeEach
    void setup(){
        comunicadoMapper = Mappers.getMapper(ComunicadoMapper.class);
        dataHora = LocalDateTime.of(2025,10,15,14,12,15);
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
        comunicacaoInDTO = ComunicacaoInDTOFixture.build(
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL);

        comunicacaoOutDTO = ComunicacaoOutDTOFixture.build(
                null,
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                statusEnvioEnum.PENDENTE
        );

        listComunicacaoEntity = List.of(new ComunicacaoEntity().builder()
                .id(null)
                .dataHoraEnvio(dataHora)
                .nomeDestinatario("teste")
                .emailDestinatario("teste@teste.com")
                .telefoneDestinatario("111111111")
                .mensagem("mensagem teste")
                .nomeRemetente("teste teste")
                .modoDeEnvio(modoEnvioEnum.EMAIL)
                .statusEnvio(statusEnvioEnum.PENDENTE)
                .build(),
                new ComunicacaoEntity().builder()
                .id(null)
                .dataHoraEnvio(dataHora)
                .nomeDestinatario("teste 2")
                .emailDestinatario("teste@teste.com 2")
                .telefoneDestinatario("111111111 2")
                .mensagem("mensagem teste 2")
                .nomeRemetente("teste teste 2")
                .modoDeEnvio(modoEnvioEnum.EMAIL)
                .statusEnvio(statusEnvioEnum.PENDENTE)
                .build());
        listComunicacaoOutDTO = List.of(ComunicacaoOutDTOFixture.build(
                null,
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                statusEnvioEnum.PENDENTE ),
                ComunicacaoOutDTOFixture.build(
                        null,
                        dataHora,
                        "teste 2",
                        "teste@teste.com 2",
                        "111111111 2",
                        "mensagem teste 2",
                        "teste teste 2",
                        modoEnvioEnum.EMAIL,
                        statusEnvioEnum.PENDENTE ));
    }

    @Test
    void deveConverterParaComunicadoEntityComSucesso(){
        ComunicacaoEntity response = comunicadoMapper.paraComunicadoEntity(comunicacaoInDTO);
        assertEquals(comunicacaoEntity,response);
    }

    @Test
    void deveConverterParaComunicadoOutDTO(){
        ComunicacaoOutDTO response = comunicadoMapper.paraComunicadoOutDTO(comunicacaoEntity);
        assertEquals(comunicacaoOutDTO, response);
    }

    @Test
    void deveConverterParaComunicadoOutDTORecebendoComunicadoInDTO(){
        ComunicacaoOutDTO response = comunicadoMapper.paraComunicadoOutDTOFromComunicacaoInDTO(comunicacaoInDTO);
        assertEquals(comunicacaoOutDTO, response);
    }

    @Test
    void deveConverterParaComunicadoInDTORecebendoComunicadoOutDTO(){
        ComunicacaoInDTO response = comunicadoMapper.paraComunicadoInDTOFromComunicacaoOutDTO(comunicacaoOutDTO);
        assertEquals(comunicacaoInDTO, response);
    }


    @Test
    void deveConverterParaListaComunicadoOutDTOComSucesso(){
        List<ComunicacaoOutDTO> response = comunicadoMapper.paraListaComunicadoOutDTO(listComunicacaoEntity);
        assertEquals(listComunicacaoOutDTO,response);
    }
}
