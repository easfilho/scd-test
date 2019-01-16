package br.com.sicredi.votacao.integration.pauta.incluir;

import br.com.sicredi.votacao.VotacaoApplication;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = VotacaoApplication.class)
@CucumberOptions(features = "src/test/resources/features/pauta/CadastrarPauta.feature", strict = true)
public class IncluirPautaTest {
}
