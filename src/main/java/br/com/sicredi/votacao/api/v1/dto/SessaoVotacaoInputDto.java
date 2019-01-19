package br.com.sicredi.votacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class SessaoVotacaoInputDto {
    @JsonIgnore
    private Long idPauta;
    private LocalTime tempoAberturaSessao;
}