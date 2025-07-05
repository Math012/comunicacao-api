package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/comunicacao")
@Tag(name = "Comunicacao", description = "Consulta, agendamento e cancelamento de mensagens")
public class ComunicacaoController {

    private final ComunicacaoService service;

    public ComunicacaoController(ComunicacaoService service) {
        this.service = service;
    }

    @PostMapping("/agendar")
    @Operation(summary = "Agendamento de mensagem", description = "Realiza o agendamento de uma mensagem")
    @ApiResponse(responseCode = "200", description = "Mensagem agendada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<ComunicacaoOutDTO> agendar(@RequestBody ComunicacaoInDTO dto)  {
        return ResponseEntity.ok(service.agendarComunicacao(dto));
    }

    @GetMapping()
    @Operation(summary = "Busca o status de uma mensagem", description = "Busca o status de uma mensagem com base no email do usuário")
    @ApiResponse(responseCode = "200", description = "Mensagem encontrada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<ComunicacaoOutDTO> buscarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }

    @PatchMapping("/cancelar")
    @Operation(summary = "Altera o status da mensagem para cancelado", description = "Altera o status da mensagem para cancelado")
    @ApiResponse(responseCode = "200", description = "Mensagem agendada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<ComunicacaoOutDTO> cancelarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }
}
