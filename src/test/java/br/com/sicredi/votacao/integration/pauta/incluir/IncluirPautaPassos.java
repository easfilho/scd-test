package br.com.sicredi.votacao.integration.pauta.incluir;

import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.api.v1.dto.PautaOutputDto;
import br.com.sicredi.votacao.integration.TestConfig;
import cucumber.api.java8.Pt;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class IncluirPautaPassos extends TestConfig implements Pt {

    private ResponseEntity<PautaOutputDto> responseEntity;
    private PautaInputDto pautaInputDto;
    private HttpStatus httpStatus;

    public IncluirPautaPassos() {
        Before(() -> {
            pautaInputDto = new PautaInputDto();
        });

        Dado("^uma pauta para tratar de \"([^\"]*)\"$", (String assunto) -> {
            pautaInputDto.setAssunto(assunto);
        });

        Dado("^uma pauta sem assunto$", () -> {
            pautaInputDto.setAssunto(null);
        });

        Quando("^incluir a pauta$", () -> {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/v1/pautas";
            HttpEntity<PautaInputDto> request = new HttpEntity<>(pautaInputDto);
            try {
                responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST,
                        request,
                        PautaOutputDto.class);
                httpStatus = responseEntity.getStatusCode();
            } catch (HttpClientErrorException e) {
                httpStatus = e.getStatusCode();
            }
        });

        Entao("^a pauta deve ser salva$", () -> {
            Assert.assertEquals(pautaInputDto.getAssunto(), Objects.requireNonNull(responseEntity.getBody()).getAssunto());
            Assert.assertTrue(Objects.requireNonNull(responseEntity.getBody().getId()) > 0L);
        });

        Entao("^a pauta não deve salva$", () -> {
            Assert.assertNull(responseEntity);
        });

        Entao("^devo receber um status \"([^\"]*)\"$", (HttpStatus httpStatusEsperado) -> {
            Assert.assertEquals(httpStatusEsperado, httpStatus);
        });
    }
}
