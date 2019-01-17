package br.com.sicredi.votacao.mapper;

import br.com.sicredi.votacao.api.v1.dto.PautaDto;
import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoOuputDto;
import br.com.sicredi.votacao.entity.Pauta;
import br.com.sicredi.votacao.entity.SessaoVotacao;
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

    public SessaoVotacaoOuputDto mapEntityParaDto(SessaoVotacao sessaoVotacao) {
        return SessaoVotacaoOuputDto.builder()
                .idSessaoVotacao(sessaoVotacao.getId())
                .validade(sessaoVotacao.getValidade())
                .pautaDto(new PautaDto(sessaoVotacao.getPauta().getId(), sessaoVotacao.getPauta().getAssunto()))
                .build();
    }
}
