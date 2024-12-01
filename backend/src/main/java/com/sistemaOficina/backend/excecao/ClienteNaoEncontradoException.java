package com.sistemaOficina.backend.excecao;


public class ClienteNaoEncontradoException extends Exception {

    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ClienteNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
