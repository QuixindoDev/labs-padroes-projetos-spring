package com.digitalinnovation.gof.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(){
        super("Cliente nao encontrado");
    }

    public ClienteNaoEncontradoException(String message){
        super(message);
    }
}
