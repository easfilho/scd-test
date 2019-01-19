package br.com.sicredi.votacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VotoInputDto {
    @JsonIgnore
    private Long idSessaoVotacao;
    @NotNull(message = "O id do cooperativado é obrigatório")
    private Long idCooperativado;
    @NotNull(message = "O voto é obrigatório")
    private Boolean voto;
}
