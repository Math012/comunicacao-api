package com.luizalebs.comunicacao_api.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.requests.ComunicacaoInDTOFixture;
import com.luizalebs.comunicacao_api.business.response.ComunicacaoOutDTOFixture;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ComunicacaoControllerTest {

    @InjectMocks
    ComunicacaoController comunicacaoController;
    @Mock
    ComunicacaoService comunicacaoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    ComunicacaoInDTO comunicacaoInDTO;

    ComunicacaoOutDTO comunicacaoOutDTO;

    ComunicacaoOutDTO comunicacaoOutDTOCancelado;

    LocalDateTime dataHora;

    ModoEnvioEnum modoEnvioEnum;

    StatusEnvioEnum statusEnvioEnum;

    String url = "/comunicacao";

    String json;

    String email;

    @BeforeEach
    void setup() throws JsonProcessingException {

        dataHora = null;
        mockMvc = MockMvcBuilders.standaloneSetup(comunicacaoController).alwaysDo(print()).build();
        comunicacaoInDTO = ComunicacaoInDTOFixture.buildController(
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                null);

        comunicacaoOutDTO = ComunicacaoOutDTOFixture.build(
                32L,
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                statusEnvioEnum.PENDENTE);

        comunicacaoOutDTOCancelado = ComunicacaoOutDTOFixture.build(
                32L,
                dataHora,
                "teste",
                "teste@teste.com",
                "111111111",
                "mensagem teste",
                "teste teste",
                modoEnvioEnum.EMAIL,
                statusEnvioEnum.CANCELADO);

        json = objectMapper.writeValueAsString(comunicacaoOutDTO);
        email = "teste@teste.com";


    }

    @Test
    void deveAgendarComunicadoComSucesso() throws Exception {
        when(comunicacaoService.agendarComunicacao(comunicacaoInDTO)).thenReturn(comunicacaoOutDTO);
        mockMvc.perform(post(url+"/agendar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
        verify(comunicacaoService,times(1)).agendarComunicacao(comunicacaoInDTO);
        verifyNoMoreInteractions(comunicacaoService);
    }

    @Test
    void deveFalharAoAgendarComunicadoComJsonNulo() throws Exception {
        //when(comunicacaoService.agendarComunicacao(comunicacaoInDTO)).thenReturn(comunicacaoOutDTO);
        mockMvc.perform(post(url+"/agendar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarStatusComSucesso() throws Exception {
        when(comunicacaoService.buscarStatusComunicacao(email)).thenReturn(comunicacaoOutDTO);
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("emailDestinatario",email)
        ).andExpect(status().isOk());
    }

    @Test
    void deveFalharAoBuscarStatusComParametroInvalido() throws Exception {
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email invalido",email)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveFalharAoBuscarStatusComParametroNulo() throws Exception {
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveCancelarStatusComSucesso() throws Exception {
        when(comunicacaoService.alterarStatusComunicacao(email)).thenReturn(comunicacaoOutDTOCancelado);
        mockMvc.perform(patch(url+"/cancelar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("emailDestinatario",email)
        ).andExpect(status().isOk());
    }

    @Test
    void deveFalharAoCancelarStatusComParametroInvalido() throws Exception {
        mockMvc.perform(patch(url+"/cancelar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email invalido",email)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveFalharAoCancelarStatusComParametroNulo() throws Exception {
        mockMvc.perform(patch(url+"/cancelar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}