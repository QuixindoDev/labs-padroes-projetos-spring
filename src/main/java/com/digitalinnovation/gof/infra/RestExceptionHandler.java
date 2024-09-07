package com.digitalinnovation.gof.infra;

import com.digitalinnovation.gof.exceptions.ClienteNaoEncontradoException;
import com.digitalinnovation.gof.exceptions.RequisicaoInvalidaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    private ResponseEntity<String> clienteNaoEncontrado(ClienteNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    private ResponseEntity<String> requisicaoInvalida(RequisicaoInvalidaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
