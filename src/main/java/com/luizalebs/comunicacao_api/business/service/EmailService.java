package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.client.EmailClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviarEmail(ComunicacaoOutDTO comunicacaoOutDTO){
        emailClient.enviarMensagem(comunicacaoOutDTO);
    }
}
