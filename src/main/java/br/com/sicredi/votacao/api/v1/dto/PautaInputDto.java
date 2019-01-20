package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PautaInputDto {
    @NotBlank(message = "O assunto da pauta é obrigatório")
    private String assunto;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
