package br.com.sicredi.votacao.api.v1.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
