package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.entity.Voto;
import br.com.sicredi.votacao.repository.CooperativadoRepository;
import br.com.sicredi.votacao.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class VotoFactory {

    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private CooperativadoRepository cooperativadoRepository;

    @Autowired
    public VotoFactory(SessaoVotacaoRepository sessaoVotacaoRepository, CooperativadoRepository cooperativadoRepository) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.cooperativadoRepository = cooperativadoRepository;
    }

    public Voto criar(VotoInputDto votoInputDto) {
        return Voto.builder()
                .sessaoVotacao(sessaoVotacaoRepository.findByIdWithPauta(votoInputDto.getIdSessaoVotacao())
                        .orElseThrow(EntityNotFoundException::new))
                .voto(votoInputDto.getVoto())
                .cooperativado(cooperativadoRepository.findById(votoInputDto.getIdCooperativado())
                        .orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
