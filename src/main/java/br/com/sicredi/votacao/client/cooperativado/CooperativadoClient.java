package br.com.sicredi.votacao.client.cooperativado;

import br.com.sicredi.votacao.enumerator.StatusCooperativadoEnum;

public interface CooperativadoClient {
    StatusCooperativadoEnum validarCpf(String cpf);
}
