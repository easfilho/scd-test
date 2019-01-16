package br.com.sicredi.votacao.repository;

import br.com.sicredi.votacao.entity.Pauta;
import org.springframework.stereotype.Repository;

@Repository
public class PautaRepository {

    public Pauta incluir(Pauta pauta) {
        pauta.setId(1L);
        return pauta;
    }
}
