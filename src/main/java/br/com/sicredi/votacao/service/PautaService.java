package br.com.sicredi.votacao.service;

import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.entity.Pauta;
import br.com.sicredi.votacao.mapper.Mapper;
import br.com.sicredi.votacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    private PautaRepository pautaRepository;
    private Mapper mapper;

    @Autowired
    public PautaService(PautaRepository pautaRepository, Mapper mapper) {
        this.pautaRepository = pautaRepository;
        this.mapper = mapper;
    }

    public Pauta incluir(PautaInputDto pautaInputDto) {
        Pauta pauta = mapper.mapDtoParaEntity(pautaInputDto);
        return pautaRepository.save(pauta);
    }

}
