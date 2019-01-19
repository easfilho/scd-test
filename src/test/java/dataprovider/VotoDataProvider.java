package dataprovider;

import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.entity.Voto;
import br.com.sicredi.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotoDataProvider {

    private CooperativadoDataProvider cooperativadoDataProvider;
    private VotoRepository votoRepository;

    @Autowired
    public VotoDataProvider(CooperativadoDataProvider cooperativadoDataProvider,
                            VotoRepository votoRepository) {
        this.cooperativadoDataProvider = cooperativadoDataProvider;
        this.votoRepository = votoRepository;
    }

    public Voto criar(SessaoVotacao sessaoVotacao) {
        Voto voto = Voto.builder()
                .voto(true)
                .cooperativado(cooperativadoDataProvider.criar())
                .sessaoVotacao(sessaoVotacao)
                .build();
        return votoRepository.save(voto);
    }
}
