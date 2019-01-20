package br.com.sicredi.votacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
