package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class SessaoVotacaoInputDto {
    private Long idPauta;
    private LocalTime tempoAberturaSessao;
}
