package br.com.sicredi.votacao.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkDto {
    private String href;
    private String rel;
    private String type;

}
