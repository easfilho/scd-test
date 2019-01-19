package br.com.sicredi.votacao.api.v1.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContagemVotosOutputDto {
    private Long totalVotos;
    private Long votosSim;
    private Long votosNao;
}
