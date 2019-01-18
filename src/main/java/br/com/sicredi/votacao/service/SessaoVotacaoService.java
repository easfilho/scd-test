package br.com.sicredi.votacao.service;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.factory.SessaoVotacaoFactory;
import br.com.sicredi.votacao.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessaoVotacaoService {

    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private SessaoVotacaoFactory sessaoVotacaoFactory;

    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository sessaoVotacaoRepository, SessaoVotacaoFactory sessaoVotacaoFactory) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.sessaoVotacaoFactory = sessaoVotacaoFactory;
    }

    public SessaoVotacao incluir(SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        SessaoVotacao sessaoVotacao = sessaoVotacaoFactory.criar(sessaoVotacaoInputDto);
        return sessaoVotacaoRepository.save(sessaoVotacao);
    }
}
