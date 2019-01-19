package br.com.sicredi.votacao.client.cooperativado;

import br.com.sicredi.votacao.client.cooperativado.dto.ValidacaoCooperativadoDto;
import br.com.sicredi.votacao.enumerator.StatusCooperativadoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component(value = "restClient")
public class CooperativadoRestClient implements CooperativadoClient {

    @Value("${url.validacao.cooperativado}")
    private String urlValidacaoCooperativado;
    private RestTemplate restTemplate;

    @Autowired
    public CooperativadoRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public StatusCooperativadoEnum validarCpf(String cpf) {
        try {
            ValidacaoCooperativadoDto validacaoCooperativadoDto = restTemplate.exchange(formatarUrlValidacaoCooperativado(cpf),
                    HttpMethod.GET,
                    null,
                    ValidacaoCooperativadoDto.class)
                    .getBody();
            return StatusCooperativadoEnum.valueOf(validacaoCooperativadoDto.getStatus());
        } catch (HttpClientErrorException e) {
            return StatusCooperativadoEnum.UNABLE_TO_VOTE;
        }
    }

    public String formatarUrlValidacaoCooperativado(String cpf) {
        return new StringBuilder()
                .append(urlValidacaoCooperativado)
                .append("/users/")
                .append(cpf)
                .toString();
    }
}
