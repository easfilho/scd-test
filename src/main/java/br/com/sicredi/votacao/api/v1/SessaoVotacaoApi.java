package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.PautaOutputDto;
import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.factory.SessaoVotacaoOutputDtoFactory;
import br.com.sicredi.votacao.service.SessaoVotacaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class SessaoVotacaoApi implements v1 {

    private SessaoVotacaoService sessaoVotacaoService;
    private SessaoVotacaoOutputDtoFactory sessaoVotacaoOutputDtoFactory;

    @Autowired
    public SessaoVotacaoApi(SessaoVotacaoService sessaoVotacaoService,
                            SessaoVotacaoOutputDtoFactory sessaoVotacaoOutputDtoFactory) {
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.sessaoVotacaoOutputDtoFactory = sessaoVotacaoOutputDtoFactory;
    }

    @PostMapping(value = "/pautas/{idPauta}/sessoes-votacao")
    @ApiOperation(value = "Api para abrir uma sessão de votação",
            notes = "A sessão de votação ficará aberta pelo tempo que for passado através do parâmetro do body" +
                    " tempoAberturaSessao. Caso não seja informado nenhum valor a sessão ficará aberta por 1 minuto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Abertura da sessão de votação realizada com sucesso",
                    response = PautaOutputDto.class),
    })
    public ResponseEntity<?> incluirSessaoVotacao(@PathVariable Long idPauta,
                                                  @RequestBody SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        sessaoVotacaoInputDto.setIdPauta(idPauta);
        return Stream.of(sessaoVotacaoInputDto)
                .map(sessaoVotacaoService::incluir)
                .map(sessaoVotacaoOutputDtoFactory::criar)
                .map(sessaoVotacaoOuputDto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(sessaoVotacaoOuputDto))
                .findFirst()
                .get();
    }
}
