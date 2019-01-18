package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.api.v1.dto.VotoInputDto;
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
                                         @RequestBody VotoInputDto votoInputDto) {
        votoInputDto.setIdSessaoVotacao(idSessaoVotacao);
        return Stream.of(votoInputDto)
                .map(votoService::incluir)
                .map(votoOutputDtoFactory::criar)
                .map(votoOutputDto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(votoOutputDto))
                .findFirst()
                .get();
    }
}
