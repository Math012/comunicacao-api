package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-08T23:46:30-0300",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UpdateComunicadoMapperImpl implements UpdateComunicadoMapper {

    @Override
    public ComunicacaoEntity updateComunicado(ComunicacaoOutDTO comunicacaoOutDTO, ComunicacaoEntity comunicacaoEntity) {
        if ( comunicacaoOutDTO == null ) {
            return comunicacaoEntity;
        }

        if ( comunicacaoOutDTO.getId() != null ) {
            comunicacaoEntity.setId( comunicacaoOutDTO.getId() );
        }
        if ( comunicacaoOutDTO.getDataHoraEnvio() != null ) {
            comunicacaoEntity.setDataHoraEnvio( comunicacaoOutDTO.getDataHoraEnvio() );
        }
        if ( comunicacaoOutDTO.getNomeDestinatario() != null ) {
            comunicacaoEntity.setNomeDestinatario( comunicacaoOutDTO.getNomeDestinatario() );
        }
        if ( comunicacaoOutDTO.getEmailDestinatario() != null ) {
            comunicacaoEntity.setEmailDestinatario( comunicacaoOutDTO.getEmailDestinatario() );
        }
        if ( comunicacaoOutDTO.getTelefoneDestinatario() != null ) {
            comunicacaoEntity.setTelefoneDestinatario( comunicacaoOutDTO.getTelefoneDestinatario() );
        }
        if ( comunicacaoOutDTO.getMensagem() != null ) {
            comunicacaoEntity.setMensagem( comunicacaoOutDTO.getMensagem() );
        }
        if ( comunicacaoOutDTO.getNomeRemetente() != null ) {
            comunicacaoEntity.setNomeRemetente( comunicacaoOutDTO.getNomeRemetente() );
        }
        if ( comunicacaoOutDTO.getModoDeEnvio() != null ) {
            comunicacaoEntity.setModoDeEnvio( comunicacaoOutDTO.getModoDeEnvio() );
        }
        if ( comunicacaoOutDTO.getStatusEnvio() != null ) {
            comunicacaoEntity.setStatusEnvio( comunicacaoOutDTO.getStatusEnvio() );
        }

        return comunicacaoEntity;
    }
}
