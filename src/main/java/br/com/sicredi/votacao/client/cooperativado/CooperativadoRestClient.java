package br.com.sicredi.votacao.client.cooperativado;

import br.com.sicredi.votacao.client.cooperativado.dto.ValidacaoCooperativadoDto;
import br.com.sicredi.votacao.enumerator.StatusCooperativadoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger;

    @Autowired
    public CooperativadoRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public StatusCooperativadoEnum validarCpf(String cpf) {
        try {
            String url = formatarUrlValidacaoCooperativado(cpf);
            logger.info("[Validação-Cpf] Iniciando validação do cpf através da url %s", url);
            ValidacaoCooperativadoDto validacaoCooperativadoDto = restTemplate.exchange(url,
                    HttpMethod.GET,
                    null,
                    ValidacaoCooperativadoDto.class)
                    .getBody();
            logger.info("[Validação-Cpf] Resultado da validação do cpf %s: %s", cpf, validacaoCooperativadoDto);
            return StatusCooperativadoEnum.get(validacaoCooperativadoDto.getStatus());
        } catch (HttpClientErrorException e) {
            logger.info("[Validação-Cpf] Cpf inválido %s. Detalhe erro %s", cpf, e.getMessage());
            return StatusCooperativadoEnum.DESABILITADO_PARA_VOTAR;
        }
    }

    private String formatarUrlValidacaoCooperativado(String cpf) {
        return new StringBuilder()
                .append(urlValidacaoCooperativado)
                .append("/users/")
                .append(cpf)
                .toString();
    }
}
