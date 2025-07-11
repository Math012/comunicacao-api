package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@AllArgsConstructor
@Component
public class ComunicacaoConverter {
    public ComunicacaoEntity paraEntity(ComunicacaoInDTO dto) {
        return ComunicacaoEntity.builder()
                .dataHoraEnvio(dto.getDataHoraEnvio())
                .emailDestinatario(dto.getEmailDestinatario())
                .nomeDestinatario(dto.getNomeDestinatario())
                .mensagem(dto.getMensagem())
                .nomeRemetente(dto.getNomeRemetente())
                .modoDeEnvio(dto.getModoDeEnvio())
                .statusEnvio(dto.getStatusEnvio())
                .telefoneDestinatario(dto.getTelefoneDestinatario())
                .build();
    }

    public ComunicacaoOutDTO paraDTO(ComunicacaoEntity entity) {
        return ComunicacaoOutDTO.builder()
                .dataHoraEnvio(entity.getDataHoraEnvio())
                .emailDestinatario(entity.getEmailDestinatario())
                .nomeDestinatario(entity.getNomeDestinatario())
                .mensagem(entity.getMensagem())
                .nomeRemetente(entity.getNomeRemetente())
                .modoDeEnvio(entity.getModoDeEnvio())
                .telefoneDestinatario(entity.getTelefoneDestinatario())
                .statusEnvio(entity.getStatusEnvio())
                .build();
    }

    public List<ComunicacaoOutDTO> paraListaDTO(List<ComunicacaoEntity> listaEntity){
        List<ComunicacaoOutDTO> listaComunicadoDTO = new ArrayList<>();
        ComunicacaoOutDTO dto = new ComunicacaoOutDTO();
        for (ComunicacaoEntity entity: listaEntity){
            dto.setDataHoraEnvio(entity.getDataHoraEnvio());
            dto.setNomeDestinatario(entity.getNomeDestinatario());
            dto.setEmailDestinatario(entity.getEmailDestinatario());
            dto.setTelefoneDestinatario(entity.getTelefoneDestinatario());
            dto.setMensagem(entity.getMensagem());
            dto.setNomeRemetente(entity.getNomeRemetente());
            dto.setModoDeEnvio(entity.getModoDeEnvio());
            dto.setStatusEnvio(entity.getStatusEnvio());
            listaComunicadoDTO.add(dto);
        }
        return listaComunicadoDTO;
    }

    public ComunicacaoEntity updateComunicado(ComunicacaoOutDTO comunicacaoOutDTO, ComunicacaoEntity comunicacaoEntity){
        return ComunicacaoEntity.builder()
                .id(comunicacaoEntity.getId())
                .dataHoraEnvio(comunicacaoOutDTO.getDataHoraEnvio() != null ? comunicacaoOutDTO.getDataHoraEnvio() : comunicacaoEntity.getDataHoraEnvio())
                .nomeDestinatario(comunicacaoOutDTO.getNomeDestinatario() != null ? comunicacaoOutDTO.getNomeDestinatario() : comunicacaoEntity.getNomeDestinatario())
                .emailDestinatario(comunicacaoOutDTO.getEmailDestinatario() != null ? comunicacaoOutDTO.getEmailDestinatario() : comunicacaoEntity.getEmailDestinatario())
                .telefoneDestinatario(comunicacaoOutDTO.getTelefoneDestinatario() != null ? comunicacaoOutDTO.getTelefoneDestinatario() : comunicacaoEntity.getTelefoneDestinatario())
                .mensagem(comunicacaoOutDTO.getMensagem() != null ? comunicacaoOutDTO.getMensagem() : comunicacaoEntity.getMensagem())
                .nomeRemetente(comunicacaoOutDTO.getNomeRemetente() != null ? comunicacaoOutDTO.getNomeRemetente() : comunicacaoEntity.getNomeRemetente())
                .modoDeEnvio(comunicacaoOutDTO.getModoDeEnvio() != null ? comunicacaoOutDTO.getModoDeEnvio() : comunicacaoEntity.getModoDeEnvio())
                .statusEnvio(comunicacaoOutDTO.getStatusEnvio() != null ? comunicacaoOutDTO.getStatusEnvio() : comunicacaoEntity.getStatusEnvio())
                .build();
    }
}
