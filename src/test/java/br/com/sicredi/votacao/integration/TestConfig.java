package br.com.sicredi.votacao.integration;

import br.com.sicredi.votacao.VotacaoApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = {VotacaoApplication.class})
public class TestConfig {
}
