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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@ContextConfiguration(classes = PautaDataProvider.class)
public class AbrirSessaoVotacaoPassos extends TestConfig implements Pt {

    private final static String URL_ABRIR_SESSAO_VOTACAO = "http://localhost:8080/v1/pautas/%d/sessoes-votacao";
    private SessaoVotacaoInputDto sessaoVotacaoInputDto;
    private ResponseEntity<SessaoVotacaoOuputDto> responseEntity;
    private HttpStatus httpStatus;
    private LocalTime tempoInformado;
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
            tempoInformado = LocalTime.parse(tempoVotacao);
            sessaoVotacaoInputDto.setHoras(tempoInformado.getHour());
            sessaoVotacaoInputDto.setMinutos(tempoInformado.getMinute());
        });

        Dado("^que não é inforamdo o tempo de votação$", () -> {
            sessaoVotacaoInputDto.setHoras(null);
            sessaoVotacaoInputDto.setMinutos(null);
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
            Assert.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getIdSessaoVotacao() > 0L);
        });

        Entao("^a data de validade para a votação deve ser a data atual mais o tempo informado$", () -> {
            LocalDateTime validadeEsperada = LocalDateTime.now().plusHours(tempoInformado.getHour())
                    .plusMinutes(tempoInformado.getMinute());
            Assert.assertEquals(validadeEsperada.getYear(), responseEntity.getBody().getValidade().getYear());
            Assert.assertEquals(validadeEsperada.getMonth(), responseEntity.getBody().getValidade().getMonth());
            Assert.assertEquals(validadeEsperada.getDayOfMonth(), responseEntity.getBody().getValidade().getDayOfMonth());
            Assert.assertEquals(validadeEsperada.getHour(), responseEntity.getBody().getValidade().getHour());
            Assert.assertEquals(validadeEsperada.getMinute(), responseEntity.getBody().getValidade().getMinute());
        });

        Entao("^a data de validade para a votação deve ser a data atual mais (\\d+) minuto$", (Long minutos) -> {
            LocalDateTime validadeEsperada = LocalDateTime.now().plusMinutes(minutos);
            Assert.assertEquals(validadeEsperada.getYear(), responseEntity.getBody().getValidade().getYear());
            Assert.assertEquals(validadeEsperada.getMonth(), responseEntity.getBody().getValidade().getMonth());
            Assert.assertEquals(validadeEsperada.getDayOfMonth(), responseEntity.getBody().getValidade().getDayOfMonth());
            Assert.assertEquals(validadeEsperada.getHour(), responseEntity.getBody().getValidade().getHour());
            Assert.assertEquals(validadeEsperada.getMinute(), responseEntity.getBody().getValidade().getMinute());
        });

        Entao("^devo receber um status \"([^\"]*)\"$", (HttpStatus httpStatusEsperado) -> {
            Assert.assertEquals(httpStatusEsperado, httpStatus);
        });
    }

    private String formatarUrl(Pauta pauta) {
        return String.format(URL_ABRIR_SESSAO_VOTACAO, pauta.getId());
    }
}
