package br.com.sicredi.votacao.integration.voto.contar;

import br.com.sicredi.votacao.api.v1.dto.ContagemVotosOutputDto;
import br.com.sicredi.votacao.entity.SessaoVotacao;
import br.com.sicredi.votacao.integration.TestConfig;
import cucumber.api.java8.Pt;
import dataprovider.CooperativadoDataProvider;
import dataprovider.PautaDataProvider;
import dataprovider.SessaoVotacaoDataProvider;
import dataprovider.VotoCooperativadoDataProvider;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@ContextConfiguration(classes = {SessaoVotacaoDataProvider.class,
        PautaDataProvider.class,
        CooperativadoDataProvider.class,
        VotoCooperativadoDataProvider.class})
public class ContarVotosPassos extends TestConfig implements Pt {

    private final static String URL_CONTAR_VOTOS = "http://localhost:8080/v1/sessoes-votacao/%d/contagem-votos";
    private ResponseEntity<ContagemVotosOutputDto> responseEntity;
    private HttpStatus httpStatus;
    @Autowired
    private SessaoVotacaoDataProvider sessaoVotacaoDataProvider;
    @Autowired
    private VotoCooperativadoDataProvider votoCooperativadoDataProvider;
    private SessaoVotacao sessaoVotacao;

    public ContarVotosPassos() {
        Dado("^uma sessão de votação$", () -> {
            sessaoVotacao = sessaoVotacaoDataProvider.criarAberta();
        });

        Dado("^uma sessão inexistente$", () -> {
            sessaoVotacao = SessaoVotacao.builder()
                    .id(999L)
                    .build();
        });

        Dado("^que existem (\\d+) votos para \"([^\"]*)\"$", (Integer numeroVotos, Boolean voto) -> {
            for (int i = 0; i < numeroVotos; i++) {
                votoCooperativadoDataProvider.criar(sessaoVotacao, voto);
            }
        });

        Quando("^contar os votos$", () -> {
            RestTemplate restTemplate = new RestTemplate();
            String url = formatarUrl(sessaoVotacao);
            try {
                responseEntity = restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        ContagemVotosOutputDto.class);
                httpStatus = responseEntity.getStatusCode();
            } catch (HttpClientErrorException e) {
                httpStatus = e.getStatusCode();
            }
        });

        Então("^devo receber o total de (\\d+) votos$", (Long totalVotosEsperados) -> {
            Assert.assertEquals(totalVotosEsperados, Objects.requireNonNull(responseEntity.getBody()).getTotalVotos());
        });

        Então("^devo receber (\\d+) votos para \"([^\"]*)\"$", (Long votosEsperados, Boolean voto) -> {
            if (voto) {
                Assert.assertEquals(votosEsperados, Objects.requireNonNull(responseEntity.getBody()).getVotosSim());
            } else {
                Assert.assertEquals(votosEsperados, Objects.requireNonNull(responseEntity.getBody()).getVotosNao());
            }
        });

        Entao("^devo receber um status \"([^\"]*)\"$", (HttpStatus httpStatusEsperado) -> {
            Assert.assertEquals(httpStatusEsperado, httpStatus);
        });
    }

    private String formatarUrl(SessaoVotacao sessaoVotacao) {
        return String.format(URL_CONTAR_VOTOS, sessaoVotacao.getId());
    }
}
