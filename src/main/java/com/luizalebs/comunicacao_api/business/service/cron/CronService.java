package com.luizalebs.comunicacao_api.business.service.cron;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicadoMapper;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.business.service.EmailService;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CronService {

    private final ComunicacaoService comunicacaoService;
    private final EmailService emailService;
    private final Clock clock;
    private final ComunicadoMapper comunicadoMapper;


    @Scheduled(cron = "${cron.horario}")
    public void enviarMensagem(){
        LocalDateTime horaInicial = LocalDateTime.now(clock);
        LocalDateTime horaFinal = LocalDateTime.now(clock).plusHours(1);
        List<ComunicacaoOutDTO> listaMensagens = comunicacaoService.buscarMensagemPorPeriado(horaInicial,horaFinal);
        listaMensagens.forEach(mensagem ->{
            emailService.enviarEmail(mensagem);
            System.out.println("email enviado: " + mensagem.getEmailDestinatario());
            mensagem.setStatusEnvio(StatusEnvioEnum.ENVIADO);
            ComunicacaoInDTO comunicacaoInDTO = comunicadoMapper.paraComunicadoInDTOFromComunicacaoOutDTO(mensagem);
            comunicacaoService.updateComunicado(mensagem.getEmailDestinatario(),comunicacaoInDTO);
        });
    }
}
