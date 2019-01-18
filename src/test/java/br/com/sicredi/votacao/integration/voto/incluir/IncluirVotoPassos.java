package br.com.sicredi.votacao.integration.voto.incluir;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.api.v1.dto.VotoOutputDto;
import br.com.sicredi.votacao.entity.Cooperativado;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.integration.TestConfig;
import cucumber.api.java8.Pt;
import dataprovider.CooperativadoDataProvider;
import dataprovider.PautaDataProvider;
import dataprovider.SessaoVotacaoDataProvider;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@ContextConfiguration(classes = {SessaoVotacaoDataProvider.class,
        PautaDataProvider.class,
        CooperativadoDataProvider.class})
public class IncluirVotoPassos extends TestConfig implements Pt {

    private final static String URL_INCLUIR_VOTO = "http://localhost:8080/v1/sessoes-votacao/%d/votos";
    private VotoInputDto votoInputDto;
    private ResponseEntity<VotoOutputDto> responseEntity;
    private HttpStatus httpStatus;
    @Autowired
    private SessaoVotacaoDataProvider sessaoVotacaoDataProvider;
    @Autowired
    private CooperativadoDataProvider cooperativadoDataProvider;
    private SessaoVotacao sessaoVotacao;

    public IncluirVotoPassos() {
        Before(() -> {
            votoInputDto = new VotoInputDto();
        });

        Dado("^uma sessão de votação aberta$", () -> {
            sessaoVotacao = sessaoVotacaoDataProvider.criarAberta();
        });

        Dado("^um voto para \"([^\"]*)\"$", (Boolean voto) -> {
            votoInputDto.setVoto(voto);
        });

        Quando("^incluir o voto$", () -> {
            Cooperativado cooperativado = cooperativadoDataProvider.criar();
            votoInputDto.setIdCooperativado(cooperativado.getId());

            RestTemplate restTemplate = new RestTemplate();
            String url = formatarUrl(sessaoVotacao);
            HttpEntity<VotoInputDto> request = new HttpEntity<>(votoInputDto);
            try {
                responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST,
                        request,
                        VotoOutputDto.class);
                httpStatus = responseEntity.getStatusCode();
            } catch (HttpClientErrorException e) {
                httpStatus = e.getStatusCode();
            }
        });

        Entao("^o voto é salvo$", () -> {
            Assert.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getIdVoto() > 0);
        });

        Entao("^devo receber um status \"([^\"]*)\"$", (HttpStatus httpStatusEsperado) -> {
            Assert.assertEquals(httpStatusEsperado, httpStatus);
        });
    }

    private String formatarUrl(SessaoVotacao sessaoVotacao) {
        return String.format(URL_INCLUIR_VOTO, sessaoVotacao.getId());
    }
}
