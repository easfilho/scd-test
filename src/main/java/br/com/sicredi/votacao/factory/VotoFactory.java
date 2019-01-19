package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.entity.Voto;
import br.com.sicredi.votacao.exception.IllegalStateException;
import br.com.sicredi.votacao.repository.CooperativadoRepository;
import br.com.sicredi.votacao.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
public class VotoFactory {

    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private CooperativadoRepository cooperativadoRepository;

    @Autowired
    public VotoFactory(SessaoVotacaoRepository sessaoVotacaoRepository, CooperativadoRepository cooperativadoRepository) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.cooperativadoRepository = cooperativadoRepository;
    }

    public Voto criar(VotoInputDto votoInputDto) throws IllegalStateException {
        Voto voto = Voto.builder()
                .sessaoVotacao(sessaoVotacaoRepository.findByIdWithPautaAndVotos(votoInputDto.getIdSessaoVotacao())
                        .orElseThrow(EntityNotFoundException::new))
                .voto(votoInputDto.getVoto())
                .cooperativado(cooperativadoRepository.findById(votoInputDto.getIdCooperativado())
                        .orElseThrow(EntityNotFoundException::new))
                .build();
        validar(voto);
        return voto;
    }

    private void validar(Voto voto) throws IllegalStateException {
        Optional.of(voto.getSessaoVotacao())
                .filter(SessaoVotacao::isAberta)
                .orElseThrow(() -> new IllegalStateException("Sessão encerrada"));

        Optional.of(voto.getSessaoVotacao())
                .filter(sessaoVotacao -> sessaoVotacao.cooperativadoAindaNaoVotou(voto.getCooperativado()))
                .orElseThrow(() -> new IllegalStateException("Cooperativado já votou"));
    }
}
