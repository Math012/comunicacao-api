package com.luizalebs.comunicacao_api.business.service.cron;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.business.service.EmailService;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CronService {

    private final ComunicacaoService comunicacaoService;
    private final EmailService emailService;

    @Scheduled(cron = "${cron.horario}")
    public void enviarMensagem(){
        System.out.println("[CRON SERVICE]");
        System.out.println("==================================");
        LocalDateTime horaInicial = LocalDateTime.now();
        LocalDateTime horaFinal = LocalDateTime.now().plusHours(1);
        List<ComunicacaoOutDTO> listaMensagens = comunicacaoService.buscarMensagemPorPeriado(horaInicial,horaFinal);
        System.out.println("Lista encontrada: " + listaMensagens);
        listaMensagens.forEach(mensagem ->{
            emailService.enviarEmail(mensagem);
            System.out.println("Tarefa enviada");
            mensagem.setStatusEnvio(StatusEnvioEnum.ENVIADO);
            System.out.println("Alterando status para enviado");
            comunicacaoService.updateComunicado(mensagem);
        });
    }
}
