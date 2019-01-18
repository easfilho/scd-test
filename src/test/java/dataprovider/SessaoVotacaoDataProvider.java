package dataprovider;

import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessaoVotacaoDataProvider {

    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private PautaDataProvider pautaDataProvider;

    @Autowired
    public SessaoVotacaoDataProvider(SessaoVotacaoRepository sessaoVotacaoRepository, PautaDataProvider pautaDataProvider) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.pautaDataProvider = pautaDataProvider;
    }

    public SessaoVotacao criarAberta() {
        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .pauta(pautaDataProvider.criar())
                .validade(LocalDateTime.now().plusHours(1L))
                .build();
        return sessaoVotacaoRepository.save(sessaoVotacao);
    }

    public SessaoVotacao criarFechada() {
        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .pauta(pautaDataProvider.criar())
                .validade(LocalDateTime.now().plusHours(-1L))
                .build();
        return sessaoVotacaoRepository.save(sessaoVotacao);
    }
}