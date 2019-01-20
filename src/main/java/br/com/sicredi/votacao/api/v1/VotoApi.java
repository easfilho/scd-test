package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.api.v1.dto.VotoOutputDto;
import br.com.sicredi.votacao.exception.HttpException;
import br.com.sicredi.votacao.factory.ContagemVotosOutputDtoFactory;
import br.com.sicredi.votacao.factory.VotoOutputDtoFactory;
import br.com.sicredi.votacao.service.VotoCooperativadoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
public class VotoApi implements v1 {

    private VotoCooperativadoService votoCooperativadoService;
    private VotoOutputDtoFactory votoOutputDtoFactory;
    private ContagemVotosOutputDtoFactory contagemVotosOutputDtoFactory;

    @Autowired
    public VotoApi(VotoCooperativadoService votoCooperativadoService,
                   VotoOutputDtoFactory votoOutputDtoFactory,
                   ContagemVotosOutputDtoFactory contagemVotosOutputDtoFactory) {
        this.votoCooperativadoService = votoCooperativadoService;
        this.votoOutputDtoFactory = votoOutputDtoFactory;
        this.contagemVotosOutputDtoFactory = contagemVotosOutputDtoFactory;
    }

    @PostMapping(value = "/sessoes-votacao/{idSessaoVotacao}/votos")
    @ApiOperation(value = "Api para incluir um voto na sessão de votação",
            notes = "Para incluir um voto o cooperativado deve participar de sessão que ainda esteja aberta," +
                    "o cooperativado também não pode votar mais de uma vez na mesma sessão, e deve também possuir um " +
                    "cpf habilitado para dar o voto. Caso não seja informado o id da sessão de votação, id do " +
                    "cooperativado ou o voto será retornado um erro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Voto incluído com sucesso",
                    response = VotoOutputDto.class),
    })
    public ResponseEntity<?> incluirVoto(@PathVariable Long idSessaoVotacao,
                                         @Valid @RequestBody VotoInputDto votoInputDto) {
        try {
            votoInputDto.setIdSessaoVotacao(idSessaoVotacao);
            return Stream.of(votoCooperativadoService.incluir(votoInputDto))
                    .map(votoOutputDtoFactory::criar)
                    .map(votoOutputDto -> ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(votoOutputDto))
                    .findFirst()
                    .get();
        } catch (HttpException e) {
            return Stream.of(e)
                    .map(exception -> ResponseEntity
                            .status(exception.getHttpStatus())
                            .body(exception.getMessage()))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping(value = "/sessoes-votacao/{idSessaoVotacao}/contagem-votos")
    @ApiOperation(value = "Api para contar os votos de uma determinada sessão",
            notes = "Conta os votos de uma determinada sessão, caso seja informada uma sessão inexistente " +
                    "será retornado 0 votos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Voto incluído com sucesso",
                    response = VotoOutputDto.class),
    })
    public ResponseEntity<?> contarVotos(@PathVariable Long idSessaoVotacao) {
        return Stream.of(idSessaoVotacao)
                .map(votoCooperativadoService::contarVotos)
                .map(contagemVotosOutputDtoFactory::criar)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }
}
