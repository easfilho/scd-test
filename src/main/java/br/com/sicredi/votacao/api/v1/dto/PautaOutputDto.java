package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PautaOutputDto {

    private Long id;
    private String assunto;
    private List<LinkDto> links;

}
