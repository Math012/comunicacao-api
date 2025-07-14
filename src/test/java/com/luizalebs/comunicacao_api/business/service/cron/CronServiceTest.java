package com.luizalebs.comunicacao_api.business.service.cron;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicadoMapper;
import com.luizalebs.comunicacao_api.business.requests.ComunicacaoInDTOFixture;
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

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CronServiceTest {

    @InjectMocks
    CronService cronService;

    @Mock
    ComunicacaoService comunicacaoService;

    @Mock
    EmailService emailService;

    @Mock
    ComunicadoMapper comunicadoMapper;

    @Mock
    Clock clock;

    LocalDateTime dataHora;
    LocalDateTime dataHoraFutura;
    List<ComunicacaoOutDTO> listComunicacaoOutDTO;
    ModoEnvioEnum modoEnvioEnum;
    StatusEnvioEnum statusEnvioEnum;
    ComunicacaoOutDTO comunicacaoOutDTO;
    ComunicacaoOutDTO comunicacaoOutDTOEnviado;
    ComunicacaoInDTO comunicacaoInDTO;
    String email ;

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
        comunicacaoOutDTOEnviado = ComunicacaoOutDTOFixture.build(
                null,
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                statusEnvioEnum.ENVIADO);
        comunicacaoInDTO = ComunicacaoInDTOFixture.build(
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL);
        listComunicacaoOutDTO = List.of(ComunicacaoOutDTOFixture.build(
                        null,
                        dataHora,
                        "teste",
                        "teste@teste.com",
                        "111111111",
                        "mensagem teste",
                        "teste teste",
                        modoEnvioEnum.EMAIL,
                        statusEnvioEnum.ENVIADO ),
                ComunicacaoOutDTOFixture.build(
                        null,
                        dataHora,
                        "teste 2",
                        "teste@teste.com 2",
                        "111111111 2",
                        "mensagem teste 2",
                        "teste teste 2",
                        modoEnvioEnum.EMAIL,
                        statusEnvioEnum.ENVIADO ));


        email = "teste@teste.com";
        dataHora = LocalDateTime.of(2025, 7, 3, 21, 5, 19, 104556200);
        dataHoraFutura = dataHora.plusHours(1);
        Clock fixedClock = Clock.fixed(dataHora.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    void deveEnviarMensagensComSucesso(){

        when(comunicacaoService.buscarMensagemPorPeriado(dataHora,dataHoraFutura)).thenReturn(listComunicacaoOutDTO);
        doNothing().when(emailService).enviarEmail(comunicacaoOutDTOEnviado);
        when(comunicadoMapper.paraComunicadoInDTOFromComunicacaoOutDTO(comunicacaoOutDTOEnviado)).thenReturn(comunicacaoInDTO);
        when(comunicacaoService.updateComunicado(email,comunicacaoInDTO)).thenReturn(comunicacaoOutDTOEnviado);
        cronService.enviarMensagem();
    }
}
