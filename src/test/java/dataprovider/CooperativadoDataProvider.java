package dataprovider;

import br.com.sicredi.votacao.entity.Cooperativado;
import br.com.sicredi.votacao.repository.CooperativadoRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CooperativadoDataProvider {

    private CooperativadoRepository cooperativadoRepository;

    @Autowired
    public CooperativadoDataProvider(CooperativadoRepository cooperativadoRepository) {
        this.cooperativadoRepository = cooperativadoRepository;
    }

    public Cooperativado criar() {
        Cooperativado cooperativado = EnhancedRandom.random(Cooperativado.class, "id");
        return cooperativadoRepository.save(cooperativado);
    }
}
