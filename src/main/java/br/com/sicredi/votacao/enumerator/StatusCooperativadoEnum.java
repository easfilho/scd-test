package br.com.sicredi.votacao.enumerator;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusCooperativadoEnum {
    HABILITADO_PARA_VOTAR("ABLE_TO_VOTE"),
    DESABILITADO_PARA_VOTAR("UNABLE_TO_VOTE");

    private String codigo;

    StatusCooperativadoEnum(String codigo) {
        this.codigo = codigo;
    }

    public Boolean habilitadoParaVotar() {
        return this.equals(HABILITADO_PARA_VOTAR);
    }

    public static StatusCooperativadoEnum get(String codigo) {
        return Arrays.stream(values())
                .filter(status -> status.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(NoSuchFieldError::new);
    }
}
