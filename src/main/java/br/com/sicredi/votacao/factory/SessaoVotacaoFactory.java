package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class SessaoVotacaoFactory {

    private static final Long TEMPO_ABERTURA_DEFAULT = 1L;
    private PautaRepository pautaRepository;

    @Autowired
    public SessaoVotacaoFactory(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public SessaoVotacao criar(SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        return SessaoVotacao.builder()
                .pauta(pautaRepository.findById(sessaoVotacaoInputDto.getIdPauta())
                        .orElseThrow(EntityNotFoundException::new))
                .validade(calcularDataValidade(sessaoVotacaoInputDto.getHoras(), sessaoVotacaoInputDto.getMinutos()))
                .build();
    }

    private LocalDateTime calcularDataValidade(Integer horas, Integer minutos) {
        if (Optional.ofNullable(horas).isPresent() && Optional.ofNullable(minutos).isPresent()) {
            return LocalDateTime.now().plusHours(horas).plusMinutes(minutos);
        }
        return LocalDateTime.now().plusMinutes(TEMPO_ABERTURA_DEFAULT);
    }
}
