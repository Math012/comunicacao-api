package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T06:23:35-0300",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ComunicadoMapperImpl implements ComunicadoMapper {

    @Override
    public ComunicacaoEntity paraComunicadoEntity(ComunicacaoInDTO comunicacaoInDTO) {
        if ( comunicacaoInDTO == null ) {
            return null;
        }

        ComunicacaoEntity.ComunicacaoEntityBuilder comunicacaoEntity = ComunicacaoEntity.builder();

        comunicacaoEntity.dataHoraEnvio( comunicacaoInDTO.getDataHoraEnvio() );
        comunicacaoEntity.nomeDestinatario( comunicacaoInDTO.getNomeDestinatario() );
        comunicacaoEntity.emailDestinatario( comunicacaoInDTO.getEmailDestinatario() );
        comunicacaoEntity.telefoneDestinatario( comunicacaoInDTO.getTelefoneDestinatario() );
        comunicacaoEntity.mensagem( comunicacaoInDTO.getMensagem() );
        comunicacaoEntity.nomeRemetente( comunicacaoInDTO.getNomeRemetente() );
        comunicacaoEntity.modoDeEnvio( comunicacaoInDTO.getModoDeEnvio() );
        comunicacaoEntity.statusEnvio( comunicacaoInDTO.getStatusEnvio() );

        return comunicacaoEntity.build();
    }

    @Override
    public ComunicacaoOutDTO paraComunicadoOutDTO(ComunicacaoEntity comunicacaoEntity) {
        if ( comunicacaoEntity == null ) {
            return null;
        }

        ComunicacaoOutDTO.ComunicacaoOutDTOBuilder comunicacaoOutDTO = ComunicacaoOutDTO.builder();

        comunicacaoOutDTO.id( comunicacaoEntity.getId() );
        comunicacaoOutDTO.dataHoraEnvio( comunicacaoEntity.getDataHoraEnvio() );
        comunicacaoOutDTO.nomeDestinatario( comunicacaoEntity.getNomeDestinatario() );
        comunicacaoOutDTO.emailDestinatario( comunicacaoEntity.getEmailDestinatario() );
        comunicacaoOutDTO.telefoneDestinatario( comunicacaoEntity.getTelefoneDestinatario() );
        comunicacaoOutDTO.mensagem( comunicacaoEntity.getMensagem() );
        comunicacaoOutDTO.nomeRemetente( comunicacaoEntity.getNomeRemetente() );
        comunicacaoOutDTO.modoDeEnvio( comunicacaoEntity.getModoDeEnvio() );
        comunicacaoOutDTO.statusEnvio( comunicacaoEntity.getStatusEnvio() );

        return comunicacaoOutDTO.build();
    }

    @Override
    public ComunicacaoOutDTO paraComunicadoOutDTOFromComunicacaoInDTO(ComunicacaoInDTO comunicacaoInDTO) {
        if ( comunicacaoInDTO == null ) {
            return null;
        }

        ComunicacaoOutDTO.ComunicacaoOutDTOBuilder comunicacaoOutDTO = ComunicacaoOutDTO.builder();

        comunicacaoOutDTO.dataHoraEnvio( comunicacaoInDTO.getDataHoraEnvio() );
        comunicacaoOutDTO.nomeDestinatario( comunicacaoInDTO.getNomeDestinatario() );
        comunicacaoOutDTO.emailDestinatario( comunicacaoInDTO.getEmailDestinatario() );
        comunicacaoOutDTO.telefoneDestinatario( comunicacaoInDTO.getTelefoneDestinatario() );
        comunicacaoOutDTO.mensagem( comunicacaoInDTO.getMensagem() );
        comunicacaoOutDTO.nomeRemetente( comunicacaoInDTO.getNomeRemetente() );
        comunicacaoOutDTO.modoDeEnvio( comunicacaoInDTO.getModoDeEnvio() );
        comunicacaoOutDTO.statusEnvio( comunicacaoInDTO.getStatusEnvio() );

        return comunicacaoOutDTO.build();
    }

    @Override
    public ComunicacaoInDTO paraComunicadoInDTOFromComunicacaoOutDTO(ComunicacaoOutDTO comunicacaoOutDTO) {
        if ( comunicacaoOutDTO == null ) {
            return null;
        }

        ComunicacaoInDTO.ComunicacaoInDTOBuilder comunicacaoInDTO = ComunicacaoInDTO.builder();

        comunicacaoInDTO.dataHoraEnvio( comunicacaoOutDTO.getDataHoraEnvio() );
        comunicacaoInDTO.nomeDestinatario( comunicacaoOutDTO.getNomeDestinatario() );
        comunicacaoInDTO.emailDestinatario( comunicacaoOutDTO.getEmailDestinatario() );
        comunicacaoInDTO.telefoneDestinatario( comunicacaoOutDTO.getTelefoneDestinatario() );
        comunicacaoInDTO.mensagem( comunicacaoOutDTO.getMensagem() );
        comunicacaoInDTO.nomeRemetente( comunicacaoOutDTO.getNomeRemetente() );
        comunicacaoInDTO.modoDeEnvio( comunicacaoOutDTO.getModoDeEnvio() );
        comunicacaoInDTO.statusEnvio( comunicacaoOutDTO.getStatusEnvio() );

        return comunicacaoInDTO.build();
    }

    @Override
    public List<ComunicacaoOutDTO> paraListaComunicadoOutDTO(List<ComunicacaoEntity> comunicacaoEntities) {
        if ( comunicacaoEntities == null ) {
            return null;
        }

        List<ComunicacaoOutDTO> list = new ArrayList<ComunicacaoOutDTO>( comunicacaoEntities.size() );
        for ( ComunicacaoEntity comunicacaoEntity : comunicacaoEntities ) {
            list.add( paraComunicadoOutDTO( comunicacaoEntity ) );
        }

        return list;
    }
}
