package com.digitalinnovation.gof.exceptions;

public class RequisicaoInvalidaException extends RuntimeException{
    public RequisicaoInvalidaException(){
        super("Existem campos vazios na requisicao que sao obrigatorios");
    }

    public RequisicaoInvalidaException(String message){
        super(message);
    }

}
