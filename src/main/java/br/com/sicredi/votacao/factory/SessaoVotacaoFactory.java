package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class SessaoVotacaoFactory {

    private final static Long TEMPO_ABERTURA_DEFAULT = 1L;
    private PautaRepository pautaRepository;

    @Autowired
    public SessaoVotacaoFactory(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public SessaoVotacao criar(SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        return SessaoVotacao.builder()
                .pauta(pautaRepository.findById(sessaoVotacaoInputDto.getIdPauta())
                        .orElseThrow(EntityNotFoundException::new))
                .validade(calcularDataValidade(sessaoVotacaoInputDto.getTempoAberturaSessao()))
                .build();
    }

    private LocalDateTime calcularDataValidade(LocalTime tempoAbertuaSessao) {
        return Optional.ofNullable(tempoAbertuaSessao)
                .map(tempo -> LocalDateTime.now().plusHours(tempo.getHour()).plusMinutes(tempo.getMinute()))
                .orElse(LocalDateTime.now().plusMinutes(TEMPO_ABERTURA_DEFAULT));
    }
}
