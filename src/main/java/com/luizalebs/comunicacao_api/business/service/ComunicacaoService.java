package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicadoMapper;
import com.luizalebs.comunicacao_api.business.converter.UpdateComunicadoMapper;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exception.DateInvalid;
import com.luizalebs.comunicacao_api.infraestructure.exception.InvalidFieldsException;
import com.luizalebs.comunicacao_api.infraestructure.exception.ResourceNotFound;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ComunicacaoService {

    private final ComunicacaoRepository repository;
    private final ComunicadoMapper comunicadoMapper;
    private final UpdateComunicadoMapper updateComunicadoMapper;

    public ComunicacaoService(ComunicacaoRepository repository, ComunicadoMapper comunicadoMapper, UpdateComunicadoMapper updateComunicadoMapper) {
        this.repository = repository;
        this.comunicadoMapper = comunicadoMapper;
        this.updateComunicadoMapper = updateComunicadoMapper;
    }

    public ComunicacaoOutDTO agendarComunicacao(ComunicacaoInDTO dto) {

        if (!verifyFields(dto)){
            throw new InvalidFieldsException("Erro ao agendar comunicado: campos inválidos");
        }

        dto.setStatusEnvio(StatusEnvioEnum.PENDENTE);
        ComunicacaoEntity entity = comunicadoMapper.paraComunicadoEntity(dto);
        repository.save(entity);
        return comunicadoMapper.paraComunicadoOutDTO(entity);
    }

    public ComunicacaoOutDTO buscarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new ResourceNotFound("Erro ao buscar comunicado: e-mail não encontrado!");
        }
        return comunicadoMapper.paraComunicadoOutDTO(entity);
    }

    public ComunicacaoOutDTO alterarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new ResourceNotFound("Erro ao buscar comunicado: e-mail não encontrado!");
        }
        entity.setStatusEnvio(StatusEnvioEnum.CANCELADO);
        repository.save(entity);
        return (comunicadoMapper.paraComunicadoOutDTO(entity));
    }

    public List<ComunicacaoOutDTO> buscarMensagemPorPeriado(LocalDateTime horaInicial, LocalDateTime horaFutura){
        try {
            return comunicadoMapper.paraListaComunicadoOutDTO(repository.findByDataHoraEnvioBetweenAndStatusEnvio(horaInicial, horaFutura, StatusEnvioEnum.PENDENTE));
        }catch (DateInvalid e){
            throw new DateInvalid("Erro ao fazer a consulta, data inválida, tente novamente");
        }
    }

    public ComunicacaoOutDTO updateComunicado(ComunicacaoOutDTO comunicacaoOutDTO){
        ComunicacaoEntity entity = repository.findByEmailDestinatario(comunicacaoOutDTO.getEmailDestinatario());
        if (Objects.isNull(entity)){
            throw new ResourceNotFound("Erro ao buscar comunicado: e-mail não encontrado!");
        }
        updateComunicadoMapper.updateComunicado(comunicacaoOutDTO,entity);
        return comunicadoMapper.paraComunicadoOutDTO(repository.save(entity));
    }

    public boolean verifyFields(ComunicacaoInDTO dto){
        if (dto.getDataHoraEnvio() == null
                || dto.getNomeDestinatario() == null
                || dto.getNomeDestinatario().isBlank()
                || dto.getEmailDestinatario() == null
                || dto.getEmailDestinatario().isBlank()
                || dto.getTelefoneDestinatario() == null
                || dto.getTelefoneDestinatario().isBlank()
                || dto.getMensagem() == null
                || dto.getMensagem().isBlank()
                || dto.getNomeRemetente() == null
                || dto.getNomeRemetente().isBlank()
                || dto.getModoDeEnvio() == null
                || dto.getModoDeEnvio().name().isBlank()
        ){
            return false;
        }else {
            return true;
        }
    }
}
