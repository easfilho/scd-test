package dataprovider;

import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.entity.VotoCooperativado;
import br.com.sicredi.votacao.repository.VotoCooperativadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotoCooperativadoDataProvider {

    private CooperativadoDataProvider cooperativadoDataProvider;
    private VotoCooperativadoRepository votoCooperativadoRepository;

    @Autowired
    public VotoCooperativadoDataProvider(CooperativadoDataProvider cooperativadoDataProvider,
                                         VotoCooperativadoRepository votoCooperativadoRepository) {
        this.cooperativadoDataProvider = cooperativadoDataProvider;
        this.votoCooperativadoRepository = votoCooperativadoRepository;
    }

    public VotoCooperativado criar(SessaoVotacao sessaoVotacao) {
        VotoCooperativado votoCooperativado = VotoCooperativado.builder()
                .voto(true)
                .cooperativado(cooperativadoDataProvider.criar())
                .sessaoVotacao(sessaoVotacao)
                .build();
        return votoCooperativadoRepository.save(votoCooperativado);
    }

    public VotoCooperativado criar(SessaoVotacao sessaoVotacao, Boolean voto) {
        VotoCooperativado votoCooperativadoEntity = VotoCooperativado.builder()
                .voto(true)
                .cooperativado(cooperativadoDataProvider.criar())
                .sessaoVotacao(sessaoVotacao)
                .build();
        return votoCooperativadoRepository.save(votoCooperativadoEntity);
    }
}
