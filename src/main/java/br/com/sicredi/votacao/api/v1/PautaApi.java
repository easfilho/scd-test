package br.com.sicredi.votacao.api.v1;

import br.com.sicredi.votacao.api.v1.dto.PautaInputDto;
import br.com.sicredi.votacao.mapper.Mapper;
import br.com.sicredi.votacao.service.PautaService;
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
    private Mapper mapper;

    @Autowired
    public PautaApi(PautaService pautaService, Mapper mapper) {
        this.pautaService = pautaService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/pautas")
    public ResponseEntity<?> incluirPauta(@Valid @RequestBody PautaInputDto pautaInputDto) {
        return Stream.of(pautaInputDto)
                .map(pautaService::incluir)
                .map(mapper::mapEntityParaDto)
                .map(pautaOutputDto -> ResponseEntity.status(HttpStatus.CREATED).body(pautaOutputDto))
                .findFirst()
                .get();
    }
}
