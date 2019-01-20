package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.api.v1.dto.PautaOutputDto;
import br.com.sicredi.votacao.factory.PautaOutputDtoFactory;
import br.com.sicredi.votacao.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @Autowired
    public PautaApi(PautaService pautaService, PautaOutputDtoFactory pautaOutputDtoFactory) {
        this.pautaService = pautaService;
        this.pautaOutputDtoFactory = pautaOutputDtoFactory;
    }

    @PostMapping(value = "/pautas")
    @ApiOperation(value = "Api para incluir pautas",
            notes = "Faz a inclusão de uma pauta, caso não seja informado o assunto retornará um erro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Inclusão da pauta realizada com sucesso", response = PautaOutputDto.class),
    })
    public ResponseEntity<?> incluirPauta(@Valid @RequestBody PautaInputDto pautaInputDto) {
        return Stream.of(pautaInputDto)
                .map(pautaService::incluir)
                .map(pautaOutputDtoFactory::criar)
                .map(pautaOutputDto -> ResponseEntity.status(HttpStatus.CREATED).body(pautaOutputDto))
                .findFirst()
                .get();
    }
}
