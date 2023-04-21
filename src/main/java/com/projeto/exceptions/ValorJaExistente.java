package com.projeto.exceptions;

public class ValorJaExistente extends RuntimeException {
    public ValorJaExistente(String mensagem){
        super(mensagem);
    }
}
