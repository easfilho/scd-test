package dataprovider;

import br.com.sicredi.votacao.entity.Pauta;
import br.com.sicredi.votacao.repository.PautaRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PautaDataProvider {

    private PautaRepository pautaRepository;

    @Autowired
    public PautaDataProvider(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta criar() {
        Pauta pauta = EnhancedRandom.random(Pauta.class, "id");
        return pautaRepository.save(pauta);
    }
}
