package br.com.sicredi.votacao.service;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.factory.SessaoVotacaoFactory;
import br.com.sicredi.votacao.repository.SessacaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessaoVotacaoService {

    private SessacaoVotacaoRepository sessacaoVotacaoRepository;
    private SessaoVotacaoFactory sessaoVotacaoFactory;

    @Autowired
    public SessaoVotacaoService(SessacaoVotacaoRepository sessacaoVotacaoRepository, SessaoVotacaoFactory sessaoVotacaoFactory) {
        this.sessacaoVotacaoRepository = sessacaoVotacaoRepository;
        this.sessaoVotacaoFactory = sessaoVotacaoFactory;
    }

    public SessaoVotacao incluir(SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        SessaoVotacao sessaoVotacao = sessaoVotacaoFactory.criar(sessaoVotacaoInputDto);
        return sessacaoVotacaoRepository.save(sessaoVotacao);
    }
}
