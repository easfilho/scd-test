package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.ContagemVotosOutputDto;
import br.com.sicredi.votacao.model.ContagemVotos;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContagemVotosOutputDtoFactory {

    private static final Long NENHUM_VOTO = 0L;

    public ContagemVotosOutputDto criar(List<ContagemVotos> contagensVotos) {
        Long votoSim = contagensVotos.stream()
                .filter(contagemVotos -> contagemVotos.getVoto().equals(true))
                .findAny()
                .map(ContagemVotos::getTotal)
                .orElse(NENHUM_VOTO);
        Long votosNao = contagensVotos.stream()
                .filter(contagemVotos -> contagemVotos.getVoto().equals(false))
                .findAny()
                .map(ContagemVotos::getTotal)
                .orElse(NENHUM_VOTO);
        return ContagemVotosOutputDto.builder()
                .votosSim(votoSim)
                .votosNao(votosNao)
                .totalVotos(votoSim + votosNao)
                .build();
    }
}
