package br.com.sicredi.votacao.api.v1.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoVotacaoOuputDto {
    private Long idSessaoVotacao;
    private PautaDto pautaDto;
    private LocalDateTime validade;
}
