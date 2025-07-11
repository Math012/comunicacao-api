package com.luizalebs.comunicacao_api.infraestructure.client;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${email.url}")
public interface EmailClient {

    @PostMapping("/mensagem")
    void enviarMensagem(@RequestBody ComunicacaoOutDTO comunicacaoOutDTO);
}
