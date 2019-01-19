package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.exception.HttpException;
import br.com.sicredi.votacao.factory.VotoOutputDtoFactory;
import br.com.sicredi.votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
public class VotoApi implements v1 {

    private VotoService votoService;
    private VotoOutputDtoFactory votoOutputDtoFactory;

    @Autowired
    public VotoApi(VotoService votoService,
                   VotoOutputDtoFactory votoOutputDtoFactory) {
        this.votoService = votoService;
        this.votoOutputDtoFactory = votoOutputDtoFactory;
    }

    @PostMapping(value = "/sessoes-votacao/{idSessaoVotacao}/votos")
    public ResponseEntity<?> incluirVoto(@PathVariable Long idSessaoVotacao,
                                         @Valid @RequestBody VotoInputDto votoInputDto) {
        try {
            votoInputDto.setIdSessaoVotacao(idSessaoVotacao);
            return Stream.of(votoService.incluir(votoInputDto))
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
    public ResponseEntity<?> incluirVoto(@PathVariable Long idSessaoVotacao) {
        return ResponseEntity.ok().build();
    }
}
