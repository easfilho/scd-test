package br.com.sicredi.votacao.integration.sessaovotacao.abrir;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoOuputDto;
import br.com.sicredi.votacao.entity.Pauta;
import br.com.sicredi.votacao.integration.TestConfig;
import cucumber.api.java8.Pt;
import dataprovider.PautaDataProvider;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Objects;

@ContextConfiguration(classes = PautaDataProvider.class)
public class AbrirSessaoVotacaoPassos extends TestConfig implements Pt {

    private final static String URL_ABRIR_SESSAO_VOTACAO = "http://localhost:8080/v1/pautas/%d/sessoes-votacao";
    private SessaoVotacaoInputDto sessaoVotacaoInputDto;
    private ResponseEntity<SessaoVotacaoOuputDto> responseEntity;
    private HttpStatus httpStatus;
    @Autowired
    private PautaDataProvider pautaDataProvider;
    private Pauta pauta;

    public AbrirSessaoVotacaoPassos() {
        Before(() -> {
            sessaoVotacaoInputDto = new SessaoVotacaoInputDto();
        });

        Dado("^uma pauta$", () -> {
            pauta = pautaDataProvider.criar();
        });

        Dado("^que o tempo de votação é de \"([^\"]*)\"$", (String tempoVotacao) -> {
            sessaoVotacaoInputDto.setTempoAberturaSessao(LocalTime.parse(tempoVotacao));
        });

        Quando("^abrir a sessão de votação$", () -> {
            RestTemplate restTemplate = new RestTemplate();
            String url = formatarUrl(pauta);
            HttpEntity<SessaoVotacaoInputDto> request = new HttpEntity<>(sessaoVotacaoInputDto);
            try {
                responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST,
                        request,
                        SessaoVotacaoOuputDto.class);
                httpStatus = responseEntity.getStatusCode();
            } catch (HttpClientErrorException e) {
                httpStatus = e.getStatusCode();
            }
        });

        Entao("^a sessão de votação deve ser aberta$", () -> {
            Assert.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getId() > 0L);
        });

        Entao("^devo receber um status \"([^\"]*)\"$", (HttpStatus httpStatusEsperado) -> {
            Assert.assertEquals(httpStatusEsperado, httpStatus);
        });
    }

    private String formatarUrl(Pauta pauta) {
        return String.format(URL_ABRIR_SESSAO_VOTACAO, pauta.getId());
    }
}
