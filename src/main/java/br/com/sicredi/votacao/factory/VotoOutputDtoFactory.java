package br.com.sicredi.votacao.factory;

import br.com.sicredi.votacao.api.v1.dto.VotoOutputDto;
import br.com.sicredi.votacao.entity.Voto;
import org.springframework.stereotype.Component;

@Component
public class VotoOutputDtoFactory {

    public VotoOutputDto criar(Voto voto) {
        return VotoOutputDto.builder()
                .idPauta(voto.getSessaoVotacao().getPauta().getId())
                .idSessaoVotacao(voto.getSessaoVotacao().getId())
                .idVoto(voto.getId())
                .voto(voto.getVoto())
                .build();
    }
}
