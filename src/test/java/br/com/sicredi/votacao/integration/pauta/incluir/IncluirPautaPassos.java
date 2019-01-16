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
import org.springframework.web.client.RestTemplate;

public class IncluirPautaPassos extends TestConfig implements Pt {

    private ResponseEntity<PautaOutputDto> responseEntity;
    private PautaInputDto pautaInputDto;

    public IncluirPautaPassos() {
        Before(() -> {
            pautaInputDto = new PautaInputDto();
        });

        Dado("^uma pauta para tratar de \"([^\"]*)\"$", (String assunto) -> {
            pautaInputDto.setAssunto(assunto);
        });

        Quando("^incluir a pauta$", () -> {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/v1/pautas";
            HttpEntity<PautaInputDto> request = new HttpEntity<>(pautaInputDto);
            responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST,
                    request,
                    PautaOutputDto.class);
        });

        Entao("^a pauta deve ser salva$", () -> {
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Assert.assertEquals(pautaInputDto.getAssunto(), responseEntity.getBody().getAssunto());
        });
    }
}
