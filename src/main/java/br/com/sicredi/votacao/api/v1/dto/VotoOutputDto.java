package br.com.sicredi.votacao.api.v1.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotoOutputDto {
    private Long idVoto;
    private Long idSessaoVotacao;
    private Long idPauta;
    private Boolean voto;
}
