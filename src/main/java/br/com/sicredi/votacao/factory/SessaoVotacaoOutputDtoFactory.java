package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.VotoApi;
import br.com.sicredi.votacao.api.v1.dto.PautaDto;
import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoOuputDto;
import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SessaoVotacaoOutputDtoFactory {

    public SessaoVotacaoOuputDto criar(SessaoVotacao sessaoVotacao) {
        SessaoVotacaoOuputDto sessaoVotacaoOuputDto = SessaoVotacaoOuputDto.builder()
                .idSessaoVotacao(sessaoVotacao.getId())
                .validade(sessaoVotacao.getValidade())
                .pautaDto(new PautaDto(sessaoVotacao.getPauta().getId(), sessaoVotacao.getPauta().getAssunto()))
                .build();

        sessaoVotacaoOuputDto.add(linkTo(methodOn(VotoApi.class)
                .incluirVoto(sessaoVotacao.getId(), new VotoInputDto()))
                .withRel("VotoOutputDto"));

        return sessaoVotacaoOuputDto;
    }
}
