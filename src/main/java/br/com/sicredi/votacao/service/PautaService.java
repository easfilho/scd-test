package br.com.sicredi.votacao.service;

import br.com.sicredi.votacao.entity.Pauta;
import br.com.sicredi.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    private PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta incluir(Pauta pauta) {
        return pautaRepository.incluir(pauta);
    }

}
