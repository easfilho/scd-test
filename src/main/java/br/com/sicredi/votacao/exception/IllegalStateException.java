package br.com.sicredi.votacao.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalStateException extends HttpException {

    public IllegalStateException(String mensagem) {
        super(HttpStatus.PRECONDITION_FAILED, mensagem);
    }
}
