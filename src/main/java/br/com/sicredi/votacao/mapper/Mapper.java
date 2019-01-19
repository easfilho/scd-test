package br.com.sicredi.votacao.mapper;

import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.entity.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Pauta mapDtoParaEntity(PautaInputDto pautaInputDto) {
        return modelMapper.map(pautaInputDto, Pauta.class);
    }
}
