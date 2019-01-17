package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.SessaoVotacaoInputDto;
import br.com.sicredi.votacao.mapper.Mapper;
import br.com.sicredi.votacao.service.SessaoVotacaoService;
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

    @Autowired
    public VotacaoApi(Mapper mapper, SessaoVotacaoService sessaoVotacaoService) {
        this.mapper = mapper;
        this.sessaoVotacaoService = sessaoVotacaoService;
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
}
