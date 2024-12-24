package com.sistemaOficina.backend.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    private final String errorCode = "CLIENTE_NAO_ENCONTRADO";

    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ClienteNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
