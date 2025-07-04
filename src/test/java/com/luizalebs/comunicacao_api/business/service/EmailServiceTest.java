package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.response.ComunicacaoOutDTOFixture;
import com.luizalebs.comunicacao_api.infraestructure.client.EmailClient;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    EmailClient emailClient;
    ComunicacaoOutDTO comunicacaoOutDTO;
    LocalDateTime dataHora;
    ModoEnvioEnum modoEnvioEnum;
    StatusEnvioEnum statusEnvioEnum;

    @BeforeEach
    void setup(){
        dataHora = LocalDateTime.of(2025,10,15,14,12,15);
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
    }

    @Test
    void DeveEnviarEmailComSucesso(){
        doNothing().when(emailClient).enviarMensagem(comunicacaoOutDTO);
        emailService.enviarEmail(comunicacaoOutDTO);
        verify(emailClient,times(1)).enviarMensagem(comunicacaoOutDTO);
        verifyNoMoreInteractions(emailClient);
    }
}