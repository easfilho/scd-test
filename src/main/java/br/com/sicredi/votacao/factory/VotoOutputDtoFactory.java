package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.VotoOutputDto;
import br.com.sicredi.votacao.entity.VotoCooperativado;
import org.springframework.stereotype.Component;

@Component
public class VotoOutputDtoFactory {

    public VotoOutputDto criar(VotoCooperativado votoCooperativado) {
        return VotoOutputDto.builder()
                .idPauta(votoCooperativado.getSessaoVotacao().getPauta().getId())
                .idSessaoVotacao(votoCooperativado.getSessaoVotacao().getId())
                .idVoto(votoCooperativado.getId())
                .voto(votoCooperativado.getVoto())
                .build();
    }
}
