package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.entity.VotoCooperativado;
import br.com.sicredi.votacao.exception.IllegalStateException;
import br.com.sicredi.votacao.repository.CooperativadoRepository;
import br.com.sicredi.votacao.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
public class VotoCooperativadoFactory {

    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private CooperativadoRepository cooperativadoRepository;

    @Autowired
    public VotoCooperativadoFactory(SessaoVotacaoRepository sessaoVotacaoRepository, CooperativadoRepository cooperativadoRepository) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.cooperativadoRepository = cooperativadoRepository;
    }

    public VotoCooperativado criar(VotoInputDto votoInputDto) throws IllegalStateException {
        VotoCooperativado votoCooperativado = VotoCooperativado.builder()
                .sessaoVotacao(sessaoVotacaoRepository.findByIdWithPautaAndVotos(votoInputDto.getIdSessaoVotacao())
                        .orElseThrow(EntityNotFoundException::new))
                .voto(votoInputDto.getVoto())
                .cooperativado(cooperativadoRepository.findById(votoInputDto.getIdCooperativado())
                        .orElseThrow(EntityNotFoundException::new))
                .build();
        validar(votoCooperativado);
        return votoCooperativado;
    }

    private void validar(VotoCooperativado votoCooperativado) throws IllegalStateException {
        Optional.of(votoCooperativado.getSessaoVotacao())
                .filter(SessaoVotacao::isAberta)
                .orElseThrow(() -> new IllegalStateException("Sessão encerrada"));

        Optional.of(votoCooperativado.getSessaoVotacao())
                .filter(sessaoVotacao -> sessaoVotacao.cooperativadoAindaNaoVotou(votoCooperativado.getCooperativado()))
                .orElseThrow(() -> new IllegalStateException("Cooperativado já votou"));
    }
}
