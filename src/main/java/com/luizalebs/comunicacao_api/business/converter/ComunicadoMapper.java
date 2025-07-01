package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComunicadoMapper {

    ComunicacaoEntity paraComunicadoEntity(ComunicacaoInDTO comunicacaoInDTO);
    ComunicacaoOutDTO paraComunicadoOutDTO(ComunicacaoEntity comunicacaoEntity);
    List<ComunicacaoOutDTO> paraListaComunicadoOutDTO(List<ComunicacaoEntity> comunicacaoEntities);


}
