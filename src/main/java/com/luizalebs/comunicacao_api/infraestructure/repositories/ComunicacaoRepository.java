package com.luizalebs.comunicacao_api.infraestructure.repositories;

import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ComunicacaoRepository extends CrudRepository<ComunicacaoEntity, Long> {

    ComunicacaoEntity findByEmailDestinatario(String nomeDestinatario);
    List<ComunicacaoEntity> findByDataHoraenvioBetweenAndStatusEnvio(LocalDateTime horaAtual, LocalDateTime horaFutura, StatusEnvioEnum statusEnvioEnum);

}
