package br.com.sicredi.votacao.api.v1.dto;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoVotacaoOuputDto extends ResourceSupport {
    private Long idSessaoVotacao;
    private PautaDto pautaDto;
    private LocalDateTime validade;
}
