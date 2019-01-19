package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContagemVotosOutputDto {
    private Long totalVotos;
    private Long votosSim;
    private Long votosNao;
}
