package br.com.sicredi.votacao.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalTime;

@Getter
@Setter
public class SessaoVotacaoInputDto {
    @JsonIgnore
    private Long idPauta;
    private LocalTime tempoAberturaSessao;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
