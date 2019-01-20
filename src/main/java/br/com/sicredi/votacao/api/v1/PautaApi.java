package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.api.v1.dto.PautaOutputDto;
import br.com.sicredi.votacao.factory.PautaOutputDtoFactory;
import br.com.sicredi.votacao.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
public class PautaApi implements v1 {

    private PautaService pautaService;
    private PautaOutputDtoFactory pautaOutputDtoFactory;
    private Logger logger;

    @Autowired
    public PautaApi(PautaService pautaService, PautaOutputDtoFactory pautaOutputDtoFactory) {
        this.pautaService = pautaService;
        this.pautaOutputDtoFactory = pautaOutputDtoFactory;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @PostMapping(value = "/pautas")
    @ApiOperation(value = "Api para incluir pautas",
            notes = "Faz a inclusão de uma pauta, caso não seja informado o assunto retornará um erro.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Inclusão da pauta realizada com sucesso", response = PautaOutputDto.class),
    })
    public ResponseEntity<?> incluirPauta(@Valid @RequestBody PautaInputDto pautaInputDto) {
        try {
            return Stream.of(pautaInputDto)
                    .peek(dto -> logger.info("[Inclusão-Pauta] Iniciando inclusão da pauta com dados de entrada: {}", dto))
                    .map(pautaService::incluir)
                    .peek(pauta -> logger.info("[Inclusão-Pauta] Pauta incluída: {}", pauta))
                    .map(pautaOutputDtoFactory::criar)
                    .peek(pautaOutputDto -> logger.info("[Inclusão-Pauta] Pauta construída para retorno: {}", pautaOutputDto))
                    .map(pautaOutputDto -> ResponseEntity.status(HttpStatus.CREATED).body(pautaOutputDto))
                    .findFirst()
                    .orElseThrow(NoSuchFieldException::new);
        } catch (Exception e) {
            logger.info("[Inclusão-Pauta] Erro ao incluir pauta. Erro detalhado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
