package br.com.sicredi.votacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotoInputDto {
    @JsonIgnore
    private Long idSessaoVotacao;
    private Long idCooperativado;
    private Boolean voto;
}
