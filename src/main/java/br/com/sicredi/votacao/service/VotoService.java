package br.com.sicredi.votacao.service;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.entity.Voto;
import br.com.sicredi.votacao.factory.VotoFactory;
import br.com.sicredi.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    private VotoRepository votoRepository;
    private VotoFactory votoFactory;

    @Autowired
    public VotoService(VotoRepository votoRepository, VotoFactory votoFactory) {
        this.votoRepository = votoRepository;
        this.votoFactory = votoFactory;
    }

    public Voto incluir(VotoInputDto votoInputDto) {
        Voto voto = votoFactory.criar(votoInputDto);
        return votoRepository.save(voto);
    }
}
