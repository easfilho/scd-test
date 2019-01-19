package br.com.sicredi.votacao.enumerator;

import lombok.Getter;

@Getter
public enum StatusCooperativadoEnum {
    ABLE_TO_VOTE,
    UNABLE_TO_VOTE;

    public Boolean habilitadoParaVotar() {
        return this.equals(ABLE_TO_VOTE);
    }
}
