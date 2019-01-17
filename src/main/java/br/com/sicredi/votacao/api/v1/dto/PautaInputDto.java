package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PautaInputDto {
    @NotBlank(message = "O assunto da pauta é obrigatório")
    private String assunto;
}
