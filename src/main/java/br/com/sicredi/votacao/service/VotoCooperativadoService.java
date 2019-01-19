package br.com.sicredi.votacao.service;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.entity.VotoCooperativado;
import br.com.sicredi.votacao.exception.IllegalStateException;
import br.com.sicredi.votacao.factory.VotoCooperativadoFactory;
import br.com.sicredi.votacao.model.ContagemVotos;
import br.com.sicredi.votacao.repository.VotoCooperativadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotoCooperativadoService {

    private VotoCooperativadoRepository votoCooperativadoRepository;
    private VotoCooperativadoFactory votoCooperativadoFactory;

    @Autowired
    public VotoCooperativadoService(VotoCooperativadoRepository votoCooperativadoRepository, VotoCooperativadoFactory votoCooperativadoFactory) {
        this.votoCooperativadoRepository = votoCooperativadoRepository;
        this.votoCooperativadoFactory = votoCooperativadoFactory;
    }

    public VotoCooperativado incluir(VotoInputDto votoInputDto) throws IllegalStateException {
        VotoCooperativado votoCooperativado = votoCooperativadoFactory.criar(votoInputDto);
        return votoCooperativadoRepository.save(votoCooperativado);
    }

    public List<ContagemVotos> contarVotos(Long idSessaoVotacao) {
        return votoCooperativadoRepository.contarVotos(idSessaoVotacao);
    }
}
