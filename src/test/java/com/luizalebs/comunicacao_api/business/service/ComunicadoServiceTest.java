package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicadoMapper;
import com.luizalebs.comunicacao_api.business.converter.UpdateComunicadoMapper;
import com.luizalebs.comunicacao_api.business.requests.ComunicacaoInDTOFixture;
import com.luizalebs.comunicacao_api.business.response.ComunicacaoOutDTOFixture;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exception.InvalidFieldsException;
import com.luizalebs.comunicacao_api.infraestructure.exception.ResourceNotFound;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComunicadoServiceTest {

    @InjectMocks
    private ComunicacaoService comunicacaoService;

    @Mock
    ComunicacaoRepository comunicacaoRepository;
    @Mock
    ComunicadoMapper comunicadoMapper;
    @Mock
    UpdateComunicadoMapper updateComunicadoMapper;

    ComunicacaoInDTO comunicacaoInDTO;

    ComunicacaoInDTO comunicacaoInDTOUpdate;

    ComunicacaoOutDTO comunicacaoOutDTO;

    ComunicacaoOutDTO comunicacaoOutDTOUpdate;

    ComunicacaoOutDTO comunicacaoOutDTOCancelado;

    ComunicacaoOutDTO comunicacaoOutDTOEmailInvalido;

    ComunicacaoEntity comunicacaoEntity;

    ComunicacaoEntity comunicacaoEntityUpdate;

    ComunicacaoEntity comunicacaoEntityCancelado;

    List<ComunicacaoOutDTO> listComunicacaoOutDTO;

    List<ComunicacaoEntity> listComunicacaoEntity;

    LocalDateTime dataHora;
    LocalDateTime dataHoraFutura;

    ModoEnvioEnum modoEnvioEnum;

    StatusEnvioEnum statusEnvioEnum;

    String email;

    @BeforeEach
    void setup(){
        dataHora = LocalDateTime.of(2025,10,15,14,12,15);
        dataHoraFutura = LocalDateTime.of(2025,10,15,15,12,15);
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

        comunicacaoEntityUpdate= ComunicacaoEntity.builder()
                .id(null)
                .dataHoraEnvio(dataHora)
                .nomeDestinatario("teste")
                .emailDestinatario("teste@teste.com")
                .telefoneDestinatario("111111111")
                .mensagem("update mensagem")
                .nomeRemetente("teste teste")
                .modoDeEnvio(modoEnvioEnum.EMAIL)
                .statusEnvio(statusEnvioEnum.PENDENTE)
                .build();

        comunicacaoInDTOUpdate = ComunicacaoInDTOFixture.build(
                null,
                null,
                null,
                null,
                "update mensagem",
                null,
                null);

        comunicacaoOutDTOUpdate = ComunicacaoOutDTOFixture.build(
                null,
                null,
                null,
                null,
                null,
                "update mensagem",
                null,
                null,
                statusEnvioEnum.PENDENTE);


        comunicacaoEntityCancelado = ComunicacaoEntity.builder()
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
                statusEnvioEnum.PENDENTE);

        comunicacaoOutDTOCancelado = ComunicacaoOutDTOFixture.build(
                null,
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                statusEnvioEnum.CANCELADO);

        comunicacaoOutDTOEmailInvalido = ComunicacaoOutDTOFixture.build(
                null,
                null,
                "teste alterado",
                "email@naoregistrado.com",
                null,
                "mensagem alterada",
                null,
                null,
                null);

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
        email = "teste@teste.com";
    }

    @Test
    void deveAgendarComunicacaoComSucesso(){
        when(comunicadoMapper.paraComunicadoEntity(comunicacaoInDTO)).thenReturn(comunicacaoEntity);
        when(comunicacaoRepository.save(comunicacaoEntity)).thenReturn(comunicacaoEntity);
        when(comunicadoMapper.paraComunicadoOutDTO(comunicacaoEntity)).thenReturn(comunicacaoOutDTO);
        ComunicacaoOutDTO response = comunicacaoService.agendarComunicacao(comunicacaoInDTO);
        assertEquals(response,comunicacaoOutDTO);
        verify(comunicadoMapper, times(1)).paraComunicadoEntity(comunicacaoInDTO);
        verify(comunicacaoRepository, times(1)).save(comunicacaoEntity);
        verify(comunicadoMapper, times(1)).paraComunicadoOutDTO(comunicacaoEntity);
        verifyNoMoreInteractions(comunicadoMapper,comunicacaoRepository);
    }

    @Test
    void deveFalharAoAgendarComunicacaoSeDtoNulo(){
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class,()->{
            comunicacaoService.agendarComunicacao(null);
        });
        assertEquals("Erro ao agendar comunicado: campos inválidos",invalidFieldsException.getMessage());
    }

    @Test
    void deveBuscarStatusComunicacaoComSucesso(){
        when(comunicacaoRepository.findByEmailDestinatario(email)).thenReturn(comunicacaoEntity);
        when(comunicadoMapper.paraComunicadoOutDTO(comunicacaoEntity)).thenReturn(comunicacaoOutDTO);
        ComunicacaoOutDTO response = comunicacaoService.buscarStatusComunicacao(email);
        assertEquals(response,comunicacaoOutDTO);
        verify(comunicacaoRepository,times(1)).findByEmailDestinatario(email);
        verify(comunicadoMapper,times(1)).paraComunicadoOutDTO(comunicacaoEntity);
        verifyNoMoreInteractions(comunicacaoRepository,comunicadoMapper);
    }

    @Test
    void deveFalharAoBuscarStatusComunicacaoComEmailNulo(){
        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class,()->{
            comunicacaoService.buscarStatusComunicacao(null);
        });
        assertEquals("Erro ao buscar comunicado: e-mail não encontrado!",resourceNotFound.getMessage());
    }

    @Test
    void deveFalharAoBuscarStatusComunicacaoComEmailNaoCadastrado(){
        String emailNaoRegistrado = "email@naoregistrado.com";
        when(comunicacaoRepository.findByEmailDestinatario(emailNaoRegistrado)).thenReturn(null);
        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class,()->{
            comunicacaoService.buscarStatusComunicacao(emailNaoRegistrado);
        });
        assertEquals("Erro ao buscar comunicado: e-mail não encontrado!",resourceNotFound.getMessage());
    }

    @Test
    void deveAlterarStatusComunicacaoComSucesso(){
        when(comunicacaoRepository.findByEmailDestinatario(email)).thenReturn(comunicacaoEntity);
        when(comunicacaoRepository.save(comunicacaoEntity)).thenReturn(comunicacaoEntityCancelado);
        when(comunicadoMapper.paraComunicadoOutDTO(comunicacaoEntity)).thenReturn(comunicacaoOutDTOCancelado);
        ComunicacaoOutDTO response = comunicacaoService.alterarStatusComunicacao(email);
        assertEquals(response, comunicacaoOutDTOCancelado);
        verifyNoMoreInteractions(comunicacaoRepository,comunicadoMapper);
    }

    @Test
    void deveFalharAoAlterarStatusComunicacaoComEmailNaoCadastrado(){
        String emailNaoRegistrado = "email@naoregistrado.com";
        when(comunicacaoRepository.findByEmailDestinatario(emailNaoRegistrado)).thenReturn(null);
        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class,()->{
            comunicacaoService.alterarStatusComunicacao(emailNaoRegistrado);
        });
        assertEquals("Erro ao buscar comunicado: e-mail não encontrado!",resourceNotFound.getMessage());
    }

    @Test
    void deveFalharAoAlterarStatusComunicacaoComEmailNulo(){
        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class,()->{
            comunicacaoService.alterarStatusComunicacao(null);
        });
        assertEquals("Erro ao buscar comunicado: e-mail não encontrado!",resourceNotFound.getMessage());
    }

    @Test
    void deveBuscarComunicadoPorPeriadoComSucesso(){
        when(comunicacaoRepository.findByDataHoraEnvioBetweenAndStatusEnvio(dataHora,dataHoraFutura,statusEnvioEnum.PENDENTE)).thenReturn(listComunicacaoEntity);
        when(comunicadoMapper.paraListaComunicadoOutDTO(listComunicacaoEntity)).thenReturn(listComunicacaoOutDTO);
        List<ComunicacaoOutDTO> response = comunicacaoService.buscarMensagemPorPeriado(dataHora,dataHoraFutura);
        assertEquals(response,listComunicacaoOutDTO);
        verifyNoMoreInteractions(comunicacaoRepository,comunicadoMapper);

    }

    @Test
    void deveRealizarUpdateComunicadoComSucesso(){
        when(comunicacaoRepository.findByEmailDestinatario(email)).thenReturn(comunicacaoEntityUpdate);
        when(comunicadoMapper.paraComunicadoOutDTOFromComunicacaoInDTO(comunicacaoInDTOUpdate)).thenReturn(comunicacaoOutDTOUpdate);
        when(updateComunicadoMapper.updateComunicado(comunicacaoOutDTOUpdate,comunicacaoEntityUpdate)).thenReturn(comunicacaoEntityUpdate);
        when(comunicacaoRepository.save(comunicacaoEntityUpdate)).thenReturn(comunicacaoEntityUpdate);
        when(comunicadoMapper.paraComunicadoOutDTO(comunicacaoEntityUpdate)).thenReturn(comunicacaoOutDTOUpdate);
        ComunicacaoOutDTO response = comunicacaoService.updateComunicado(email,comunicacaoInDTOUpdate);
        assertEquals(response, comunicacaoOutDTOUpdate);
    }

    @Test
    void deveFalharAoFazerUpdateComunicadoComComunicadoDTONulo(){
        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class,()->{
            comunicacaoService.updateComunicado(null,comunicacaoInDTO);
        });
        assertEquals("Erro ao buscar comunicado: e-mail não encontrado!",resourceNotFound.getMessage());
    }

    @Test
    void deveFalharAoFazerUpdateComunicadoComEmailNaoCadastrado(){
        String emailNaoRegistrado = "email@naoregistrado.com";
        when(comunicacaoRepository.findByEmailDestinatario(emailNaoRegistrado)).thenReturn(null);
        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class,()->{
            comunicacaoService.updateComunicado(emailNaoRegistrado,comunicacaoInDTO);
        });
        assertEquals("Erro ao buscar comunicado: e-mail não encontrado!",resourceNotFound.getMessage());
    }
}