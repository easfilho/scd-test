package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Setter
public class PautaOutputDto extends ResourceSupport {
    private Long idPauta;
    private String assunto;
}
