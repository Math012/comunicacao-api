package com.luizalebs.comunicacao_api.infraestructure.client;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${email.url}")
public interface EmailClient {

    @PostMapping("/mensagem")
    public ResponseEntity<Void> enviarMensagem(@RequestBody ComunicacaoInDTO comunicacaoInDTO);
}
