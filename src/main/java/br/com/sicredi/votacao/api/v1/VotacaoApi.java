package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
import br.com.sicredi.votacao.exception.HttpException;
import br.com.sicredi.votacao.factory.VotoOutputDtoFactory;
import br.com.sicredi.votacao.mapper.Mapper;
import br.com.sicredi.votacao.service.SessaoVotacaoService;
import br.com.sicredi.votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
public class VotacaoApi implements v1 {

    private Mapper mapper;
    private SessaoVotacaoService sessaoVotacaoService;
    private VotoService votoService;
    private VotoOutputDtoFactory votoOutputDtoFactory;

    @Autowired
    public VotacaoApi(Mapper mapper,
                      SessaoVotacaoService sessaoVotacaoService,
                      VotoService votoService,
                      VotoOutputDtoFactory votoOutputDtoFactory) {
        this.mapper = mapper;
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.votoService = votoService;
        this.votoOutputDtoFactory = votoOutputDtoFactory;
    }

    @PostMapping(value = "/pautas/{idPauta}/sessoes-votacao")
    public ResponseEntity<?> incluirSessaoVotacao(@PathVariable Long idPauta,
                                                  @RequestBody SessaoVotacaoInputDto sessaoVotacaoInputDto) {
        sessaoVotacaoInputDto.setIdPauta(idPauta);
        return Stream.of(sessaoVotacaoInputDto)
                .map(sessaoVotacaoService::incluir)
                .map(mapper::mapEntityParaDto)
                .map(sessaoVotacaoOuputDto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(sessaoVotacaoOuputDto))
                .findFirst()
                .get();
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
                    .body(null);
        }
    }
}
