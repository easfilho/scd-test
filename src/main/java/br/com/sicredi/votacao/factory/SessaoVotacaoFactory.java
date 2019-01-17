package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.entity.Pauta;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class SessaoVotacaoFactory {

    private final static Long TEMPO_ABERTURA_DEFAULT = 1L;

    public SessaoVotacao criar(SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        return SessaoVotacao.builder()
                .pauta(Pauta.builder()
                        .id(sessaoVotacaoInputDto.getIdPauta())
                        .build())
                .validade(calcularDataValidade(sessaoVotacaoInputDto.getTempoAberturaSessao()))
                .build();
    }

    private LocalDateTime calcularDataValidade(LocalTime tempoAbertuaSessao) {
        return Optional.ofNullable(tempoAbertuaSessao)
                .map(tempo -> LocalDateTime.now().plusHours(tempo.getHour()).plusMinutes(tempo.getMinute()))
                .orElse(LocalDateTime.now().plusMinutes(TEMPO_ABERTURA_DEFAULT));
    }
}
