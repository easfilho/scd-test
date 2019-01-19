package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.exception.HttpException;
import br.com.sicredi.votacao.factory.ContagemVotosOutputDtoFactory;
import br.com.sicredi.votacao.factory.VotoOutputDtoFactory;
import br.com.sicredi.votacao.service.VotoCooperativadoService;
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
    public ResponseEntity<?> incluirVoto(@PathVariable Long idSessaoVotacao) {
        return Stream.of(idSessaoVotacao)
                .map(votoCooperativadoService::contarVotos)
                .map(contagemVotosOutputDtoFactory::criar)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }
}
