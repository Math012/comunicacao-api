package com.luizalebs.comunicacao_api.business.service.cron;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.response.ComunicacaoOutDTOFixture;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.business.service.EmailService;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CronServiceTest {

    @InjectMocks
    CronService cronService;

    @Mock
    ComunicacaoService comunicacaoService;

    @Mock
    EmailService emailService;


    @Mock
    Clock clock;

    LocalDateTime dataHora;
    LocalDateTime dataHoraFutura;
    List<ComunicacaoOutDTO> listComunicacaoOutDTO;
    ModoEnvioEnum modoEnvioEnum;
    StatusEnvioEnum statusEnvioEnum;
    ComunicacaoOutDTO comunicacaoOutDTO;

    @BeforeEach
    void setup(){

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

        dataHora = LocalDateTime.of(2025, 7, 3, 21, 5, 19, 104556200);
        dataHoraFutura = dataHora.plusHours(1);
        Clock fixedClock = Clock.fixed(dataHora.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());

    }

    @Test
    void deveEnviarMensagensComSucesso(){
        when(comunicacaoService.buscarMensagemPorPeriado(dataHora,dataHoraFutura)).thenReturn(listComunicacaoOutDTO);
        doNothing().when(emailService).enviarEmail(comunicacaoOutDTO);
        cronService.enviarMensagem();
    }
}
