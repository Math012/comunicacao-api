package com.luizalebs.comunicacao_api.infraestructure.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "COMUNICACAO")
public class ComunicacaoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HORA_ENVIO", nullable = false)
    private LocalDateTime dataHoraEnvio;

    @Column(name = "NOME_DESTINATARIO", nullable = false)
    private String nomeDestinatario;

    @Column(name = "EMAIL_DESTINATARIO", nullable = false, unique = true)
    private String emailDestinatario;

    @Column(name = "TELEFONE_DESTINATARIO")
    private String telefoneDestinatario;

    @Column(name = "MENSAGEM", nullable = false)
    private String mensagem;

    @Column(name = "NOME_REMETENTE")
    private String nomeRemetente;

    @Column(name = "MODO_ENVIO")
    @Enumerated(EnumType.STRING)
    private ModoEnvioEnum modoDeEnvio;

    @Column(name = "STATUS_ENVIO")
    @Enumerated(EnumType.STRING)
    private StatusEnvioEnum statusEnvio;

}
