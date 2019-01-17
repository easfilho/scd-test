package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.VotacaoApi;
import br.com.sicredi.votacao.api.v1.dto.PautaOutputDto;
import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.entity.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PautaOutputDtoFactory {

    private ModelMapper modelMapper;

    @Autowired
    public PautaOutputDtoFactory(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PautaOutputDto criar(Pauta pauta) {
        PautaOutputDto pautaOutputDto = modelMapper.map(pauta, PautaOutputDto.class);
        pautaOutputDto.setIdPauta(pauta.getId());
        pautaOutputDto.add(linkTo(methodOn(VotacaoApi.class)
                .incluirSessaoVotacao(pauta.getId(), new SessaoVotacaoInputDto()))
                .withRel("SessaoVotacaoOutputDto"));
        return pautaOutputDto;
    }
}
